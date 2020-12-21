package com.codepass.user.service;

import com.codepass.user.dao.AnswerRepository;
import com.codepass.user.dao.LikeAnswerRepository;
import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
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

    public AnswerEntity createAnswer(int userId, int questionId, String content) {
        AnswerEntity answerEntity = new AnswerEntity();
        String dockerId = questionRepository.findById(questionId).get().getDockerId();
        String newDockerId = dockerService.cloneDocker(dockerId);
        answerEntity.setAnswerer(userId);
        answerEntity.setQuestionId(questionId);
        answerEntity.setDockerId(newDockerId);
        answerEntity.setContent(content);
        answerEntity.setAnswerTime(new Timestamp(System.currentTimeMillis()));
        answerEntity.setLikeCount(0);
        answerEntity.setStatus(0);
        answerRepository.save(answerEntity);
        return answerEntity;
    }

    public void likeAnswer(int answerId) {
        LikeAnswerEntity likeAnswerEntity = new LikeAnswerEntity();
        likeAnswerEntity.setAnswerId(answerId);
        likeAnswerEntity.setLikeTime(new Timestamp(System.currentTimeMillis()));
        likeAnswerRepository.save(likeAnswerEntity);
        answerRepository.updateLikeBy(answerId, 1);
    }

    public AnswerEntity updateAnswer(int answerId, String content, boolean isFinal) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByNickname(userDetails.getUsername());
        int userId = userEntity.getId();
        AnswerEntity answerEntity = answerRepository.findById(answerId).get();
        if (answerEntity.getStatus() == 1) {
            throw new RuntimeException("不能修改已经提交的回答");
        }
        if (content != null) answerEntity.setContent(content);
        if (isFinal) answerEntity.setStatus(1);
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
}
