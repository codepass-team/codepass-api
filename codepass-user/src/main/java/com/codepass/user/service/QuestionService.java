package com.codepass.user.service;

import com.codepass.user.dao.*;
import com.codepass.user.dao.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    FollowQuestionRepository followQuestionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DockerService dockerService;
    @Autowired
    LikeQuestionRepository likeQuestionRepository;
    @Autowired
    CollectAnswerRepository collectAnswerRepository;

    public QuestionEntity createQuestion(UserEntity questioner, String title, String password) throws IOException {
        String dockerId = dockerService.createDocker(null);
        dockerService.mountDocker(dockerId, password);
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(title);
        questionEntity.setQuestioner(questioner);
        questionEntity.setStatus(0);
        questionEntity.setDockerId(dockerId);
        questionEntity.setLikeCount(0);
        questionEntity.setCollectCount(0);
        questionEntity.setCommentCount(0);
        questionEntity.setRaiseTime(new Timestamp(System.currentTimeMillis()));
        questionRepository.save(questionEntity);
        return questionEntity;
    }

    public QuestionEntity changeQuestion(Integer questionId, String title, String content, boolean isFinal) throws IOException {
        QuestionEntity questionEntity = questionRepository.findById(questionId).get();
        if (questionEntity.getStatus() == 1) {
            throw new RuntimeException("不能修改已经提交的问题");
        }
        if (title != null) questionEntity.setTitle(title);
        if (content != null) questionEntity.setContent(content);
        if (isFinal) {
            questionEntity.setStatus(1);
            dockerService.umountDocker(questionEntity.getDockerId());
        }
        questionEntity.setRaiseTime(new Timestamp(System.currentTimeMillis()));
        questionRepository.save(questionEntity);
        return questionEntity;
    }

    public void deleteQuestion(Integer questionId) {
        questionRepository.deleteById(questionId);
    }

    public List<String> suggestQuestion(String keywords, int limits) {
        // 返回最多limits条数据
        Page<QuestionEntity> questionEntityList = questionRepository.findByTitleLike(keywords, PageRequest.of(0, limits));
        return questionEntityList.stream().map(QuestionEntity::getTitle).collect(Collectors.toList());
    }

    public List<QuestionEntity> searchQuestion(String keywords, int page) {
        return questionRepository.findByTitleLikeOrContentLikeOrderByRaiseTimeDesc(keywords, keywords, PageRequest.of(page, 4)).toList();//根据页面显示情况修改页大小
    }

    public QuestionEntity getQuestion(int questionId) {
        return questionRepository.findById(questionId).get();
    }

    public List<QuestionEntity> getUserQuestion(UserEntity user, int page) {
        return questionRepository.findByQuestionerOrderByRaiseTimeDesc(user, PageRequest.of(page, 10)).toList();
    }

    public Page<QuestionEntity> getAllQuestion(int page) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "likeCount"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "raiseTime"));
        return questionRepository.findAll(PageRequest.of(page, 10, Sort.by(orders)));
    }

    public void followQuestion(UserEntity userEntity, int questionId) {
        int userId = userEntity.getId();
        if (followQuestionRepository.existsByUserIdAndQuestionId(userId, questionId))
            return;
        FollowQuestionEntity followQuestionEntity = new FollowQuestionEntity();
        followQuestionEntity.setQuestionId(questionId);
        followQuestionEntity.setUserId(userId);
        followQuestionEntity.setFollowTime(new Timestamp(System.currentTimeMillis()));
        followQuestionRepository.save(followQuestionEntity);
        questionRepository.updateCollectCountBy(questionId, 1);
    }

    public void unfollowQuestion(UserEntity userEntity, int questionId) {
        int userId = userEntity.getId();
        if (!followQuestionRepository.existsByUserIdAndQuestionId(userId, questionId))
            return;
        var pk = new FollowQuestionEntityPK();
        pk.setUserId(userId);
        pk.setQuestionId(questionId);
        followQuestionRepository.deleteById(pk);
        questionRepository.updateCollectCountBy(questionId, -1);
    }

    public void likeQuestion(UserEntity userEntity, int questionId) {
        int userId = userEntity.getId();
        if (likeQuestionRepository.existsByUserIdAndQuestionId(userId, questionId))
            return;
        LikeQuestionEntity likeQuestionEntity = new LikeQuestionEntity();
        likeQuestionEntity.setQuestionId(questionId);
        likeQuestionEntity.setUserId(userId);
        likeQuestionEntity.setLikeTime(new Timestamp(System.currentTimeMillis()));
        likeQuestionRepository.save(likeQuestionEntity);
        questionRepository.updateLikeBy(questionId, 1);
    }

    public void unlikeQuestion(UserEntity userEntity, int questionId) {
        int userId = userEntity.getId();
        if (!likeQuestionRepository.existsByUserIdAndQuestionId(userId, questionId))
            return;
        var pk = new LikeQuestionEntityPK();
        pk.setUserId(userId);
        pk.setQuestionId(questionId);
        likeQuestionRepository.deleteById(pk);
        questionRepository.updateLikeBy(questionId, -1);
    }

    public int checkLike(UserEntity userEntity, int questionId) {
        int userId = userEntity.getId();
        return likeQuestionRepository.existsByUserIdAndQuestionId(userId, questionId) ? 1 : 0;
    }

    public List<FollowQuestionEntity> getUserFollow(int userid, int page) {
        return followQuestionRepository.findByUserId(userid, PageRequest.of(page, 10)).toList();
    }
}
