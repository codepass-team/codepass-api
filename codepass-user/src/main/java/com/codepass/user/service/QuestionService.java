package com.codepass.user.service;

import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public QuestionEntity createQuestion(int questionerId, String title) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(title);
        questionEntity.setQuestioner(questionerId);
        return questionRepository.save(questionEntity);
    }

    public void changeQuestion(Integer questionId, String title, String content, boolean isFinal) {
        QuestionEntity questionEntity = questionRepository.findById(questionId).get();
        if (questionEntity.getStatus() == 1) {
            throw new RuntimeException("不能修改已经提交的问题");
        }
        if (title != null) questionEntity.setTitle(title);
        if (content != null) questionEntity.setContent(content);
        if (isFinal) questionEntity.setStatus(1);
        questionRepository.save(questionEntity);
    }

    public void deleteQuestion(Integer questionId) {
        questionRepository.deleteById(questionId);
    }

    public List<String> suggestQuestion(String keywords) {
        // 只返回10条数据
        Page<QuestionEntity> questionEntityList = questionRepository.findByTitleLike(keywords, PageRequest.of(0, 10));
        return questionEntityList.stream().map(QuestionEntity::getTitle).collect(Collectors.toList());
    }

    public List<QuestionEntity> searchQuestion(String keywords, int page) {
        return questionRepository.findByTitleLike(keywords, PageRequest.of(page, 10)).toList();
    }

    public QuestionEntity getQuestion(int questionId) {
        return questionRepository.findById(questionId).get();
    }

    public List<QuestionEntity> getUserQuestion(int userId, int page) {
        return questionRepository.findByQuestioner(userId, PageRequest.of(page, 10)).toList();
    }

    public Page<QuestionEntity> getAllQuestion(int page) {
        return questionRepository.findAll(PageRequest.of(page, 10));
    }
}
