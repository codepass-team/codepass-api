package com.codepass.user.service;

import com.codepass.user.dao.AnswerRepository;
import com.codepass.user.dao.LikeAnswerRepository;
import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AnswerEntity createAnswer(int userId, int questionId, String content) {
        AnswerEntity answerEntity = new AnswerEntity();
        String dockerId = questionRepository.findById(questionId).get().getDockerId();
        String newDockerId = dockerService.cloneDocker(dockerId);;
        answerEntity.setAnswerer(userId);
        answerEntity.setDockerId(newDockerId);
        answerEntity.setAnswerTime(new Timestamp(System.currentTimeMillis()));
        answerEntity.setLikeCount(0);
        return answerRepository.save(answerEntity);
    }

    public void likeAnswer(int answerId) {
        LikeAnswerEntity likeAnswerEntity = new LikeAnswerEntity();
        likeAnswerEntity.setAnswerId(answerId);
        likeAnswerEntity.setLikeTime(new Timestamp(System.currentTimeMillis()));
        likeAnswerRepository.save(likeAnswerEntity);
        answerRepository.updateLikeBy(answerId, 1);
    }

    public AnswerEntity updateAnswer(int answerId, String content) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContent(content);
        answerEntity.setAnswerTime(new Timestamp(System.currentTimeMillis()));


        return null;
    }
}
