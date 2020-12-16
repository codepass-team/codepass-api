package com.codepass.user.service;

import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public QuestionEntity createQuestion(String title, String content) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setTitle(title);
        questionEntity.setContent(content);
//        questionEntity.setQuestioner();
        return questionRepository.save(questionEntity);
    }

    public void changeQuestion(Integer questionId) {
        questionRepository.deleteById(questionId);
    }

    public void deleteQuestion(Integer questionId) {
        questionRepository.deleteById(questionId);
    }
}
