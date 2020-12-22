package com.codepass.user.service;

import com.codepass.user.dao.FollowQuestionRepository;
import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.FollowQuestionEntity;
import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    FollowQuestionRepository followQuestionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DockerService dockerService;

    public QuestionEntity createQuestion(UserEntity questioner, String title) throws IOException {
        String dockerId = dockerService.createDocker(null);
        dockerService.mountDocker(dockerId, "123456");
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(title);
        questionEntity.setQuestioner(questioner);
        questionEntity.setStatus(0);
        questionEntity.setDockerId(dockerId);
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
        return questionRepository.findByTitleLike(keywords, PageRequest.of(page, 4)).toList();//根据页面显示情况修改页大小
    }

    public QuestionEntity getQuestion(int questionId) {
        return questionRepository.findById(questionId).get();
    }

    public List<QuestionEntity> getUserQuestion(UserEntity user, int page) {
        return questionRepository.findByQuestioner(user, PageRequest.of(page, 10)).toList();
    }

    public Page<QuestionEntity> getAllQuestion(int page) {
        return questionRepository.findAll(PageRequest.of(page, 10));
    }

    public void followQuestion(int questionId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        int userId = userEntity.getId();
        FollowQuestionEntity followQuestionEntity = new FollowQuestionEntity();
        followQuestionEntity.setQuestionId(questionId);
        followQuestionEntity.setUserId(userId);
        followQuestionEntity.setFollowTime(new Timestamp(System.currentTimeMillis()));
        followQuestionRepository.save(followQuestionEntity);
    }
}
