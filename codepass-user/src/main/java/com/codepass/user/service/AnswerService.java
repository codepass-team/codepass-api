package com.codepass.user.service;

import com.codepass.user.dao.AnswerRepository;
import com.codepass.user.dao.LikeAnswerRepository;
import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

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

    public AnswerEntity createAnswer(UserEntity userId, int questionId, String content) {
        try {
            AnswerEntity answerEntity = new AnswerEntity();
            String dockerId = questionRepository.findById(questionId).get().getDockerId();
            String newDockerId = dockerService.createDocker(dockerId);
            dockerService.mountDocker(newDockerId, "123456");
            answerEntity.setAnswerer(userId);
            answerEntity.setQuestionId(questionId);
            answerEntity.setDockerId(newDockerId);
            answerEntity.setContent(content);
            answerEntity.setAnswerTime(new Timestamp(System.currentTimeMillis()));
            answerEntity.setLikeCount(0);
            answerEntity.setStatus(0);
            answerRepository.save(answerEntity);
            return answerEntity;
        } catch (IOException e) {
            throw new RuntimeException("Create docker failed");
        }
    }

    public void likeAnswer(int answerId) {
        LikeAnswerEntity likeAnswerEntity = new LikeAnswerEntity();
        likeAnswerEntity.setAnswerId(answerId);
        likeAnswerEntity.setLikeTime(new Timestamp(System.currentTimeMillis()));
        likeAnswerRepository.save(likeAnswerEntity);
        answerRepository.updateLikeBy(answerId, 1);
    }

    public AnswerEntity updateAnswer(int answerId, String content, boolean isFinal) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        int userId = userEntity.getId();
        AnswerEntity answerEntity = answerRepository.findById(answerId).get();
        if (answerEntity.getStatus() == 1) {
            throw new RuntimeException("不能修改已经提交的回答");
        }
        if (content != null) answerEntity.setContent(content);
        if (isFinal) {
            answerEntity.setStatus(1);
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

    public List<AnswerEntity> getUserAnswer(int userId, int page) {
        return answerRepository.findByAnswerer(userId, PageRequest.of(page, 10)).toList();
    }
}
