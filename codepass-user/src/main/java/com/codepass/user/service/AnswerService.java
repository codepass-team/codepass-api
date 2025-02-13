package com.codepass.user.service;

import com.codepass.user.dao.*;
import com.codepass.user.dao.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Component
@Transactional
public class AnswerService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    LikeAnswerRepository likeAnswerRepository;
    @Autowired
    QuestionService questionService;
    @Autowired
    DockerService dockerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CollectAnswerRepository collectAnswerRepository;

    public AnswerEntity createAnswer(UserEntity userEntity, int questionId, String password) throws IOException {
        AnswerEntity answerEntity = new AnswerEntity();
        String dockerId = questionRepository.findById(questionId).get().getDockerId();
        String newDockerId = dockerService.createDocker(dockerId);
        dockerService.mountDocker(newDockerId, password);
        answerEntity.setAnswerer(userEntity);
        answerEntity.setQuestionId(questionId);
        answerEntity.setDockerId(newDockerId);
        answerEntity.setAnswerTime(new Timestamp(System.currentTimeMillis()));
        answerEntity.setLikeCount(0);
        answerEntity.setCollectCount(0);
        answerEntity.setCommentCount(0);
        answerEntity.setStatus(0);
        answerEntity.setDiff(dockerService.getDiff(dockerId, newDockerId));
        answerRepository.save(answerEntity);
        return answerEntity;
    }

    public void likeAnswer(UserEntity userEntity, int answerId) {
        int userId = userEntity.getId();
        if (likeAnswerRepository.existsByUserIdAndAnswerId(userId, answerId))
            return;
        LikeAnswerEntity likeAnswerEntity = new LikeAnswerEntity();
        likeAnswerEntity.setAnswerId(answerId);
        likeAnswerEntity.setUserId(userId);
        likeAnswerEntity.setLikeTime(new Timestamp(System.currentTimeMillis()));
        likeAnswerRepository.save(likeAnswerEntity);
        answerRepository.updateLikeBy(answerId, 1);
    }

    public void unlikeAnswer(UserEntity userEntity, int answerId) {
        int userId = userEntity.getId();
        if (!likeAnswerRepository.existsByUserIdAndAnswerId(userId, answerId))
            return;
        LikeAnswerEntityPK pk = new LikeAnswerEntityPK();
        pk.setUserId(userId);
        pk.setAnswerId(answerId);
        likeAnswerRepository.deleteById(pk);
        answerRepository.updateLikeBy(answerId, -1);
    }

    public void collectAnswer(UserEntity userEntity, int answerId) {
        int userId = userEntity.getId();
        if (collectAnswerRepository.existsByUserIdAndAnswerId(userId, answerId))
            return;
        CollectAnswerEntity collectAnswerEntity = new CollectAnswerEntity();
        collectAnswerEntity.setAnswerId(answerId);
        collectAnswerEntity.setUserId(userId);
        collectAnswerEntity.setCollectTime(new Timestamp(System.currentTimeMillis()));
        collectAnswerRepository.save(collectAnswerEntity);
        answerRepository.updateCollectCountBy(answerId, 1);
    }

    public void uncollectAnswer(UserEntity userEntity, int answerId) {
        int userId = userEntity.getId();
        if (!collectAnswerRepository.existsByUserIdAndAnswerId(userId, answerId))
            return;
        CollectAnswerEntityPK pk = new CollectAnswerEntityPK();
        pk.setUserId(userId);
        pk.setAnswerId(answerId);
        collectAnswerRepository.deleteById(pk);
        answerRepository.updateCollectCountBy(answerId, -1);
    }

    public AnswerEntity updateAnswer(UserEntity userEntity, int answerId, String content, boolean isFinal) throws IOException {
        AnswerEntity answerEntity = answerRepository.findById(answerId).get();
        QuestionEntity questionEntity = questionRepository.findById(answerEntity.getQuestionId()).get();
        if (answerEntity.getAnswerer().getId() != userEntity.getId()) {
            throw new RuntimeException("不能修改别人的回答");
        }
        if (answerEntity.getStatus() == 1) {
            throw new RuntimeException("不能修改已经提交的回答");
        }
        if (content != null) answerEntity.setContent(content);
        if (isFinal) {
            answerEntity.setStatus(1);
            answerEntity.setDiff(dockerService.getDiff(questionEntity.getDockerId(), answerEntity.getDockerId()));
            dockerService.umountDocker(answerEntity.getDockerId());
        }
        answerEntity.setAnswerTime(new Timestamp(System.currentTimeMillis()));
        answerRepository.save(answerEntity);
        return answerEntity;
    }

    public void deleteAnswer(int answerId) {
        answerRepository.deleteById(answerId);
    }

    public AnswerEntity getAnswer(int answerId) {
        return answerRepository.findById(answerId).get();
    }

    public List<AnswerEntity> getUserAnswer(UserEntity user, int page) {
        return answerRepository.findByAnswerer(user, PageRequest.of(page, 10)).toList();
    }

    public List<CollectAnswerEntity> getUserCollect(int userid, int page) {
        return collectAnswerRepository.findByUserId(userid, PageRequest.of(page, 10)).toList();
    }

    public Page<AnswerEntity> getAllAnswer(int page) {
        return answerRepository.findAll(PageRequest.of(page, 10));
    }
}
