package com.codepass.user.service;

import com.codepass.user.dao.AnswerCommentRepository;
import com.codepass.user.dao.AnswerRepository;
import com.codepass.user.dao.QuestionCommentRepository;
import com.codepass.user.dao.QuestionRepository;
import com.codepass.user.dao.entity.AnswerCommentEntity;
import com.codepass.user.dao.entity.QuestionCommentEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;


@Component
@Transactional
public class CommentService {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerCommentRepository answerCommentRepository;
    @Autowired
    QuestionCommentRepository questionCommentRepository;

    public Page<QuestionCommentEntity> getQuestionCommentById(int questionId, int page) {
        return questionCommentRepository.findByQuestionId(questionId, PageRequest.of(page, 10));
    }

    public Page<AnswerCommentEntity> getAnswerCommentById(int answerId, int page) {
        return answerCommentRepository.findByAnswerId(answerId, PageRequest.of(page, 10));
    }

    public QuestionCommentEntity commentQuestion(UserEntity userEntity, int questionId, String content) {
        QuestionCommentEntity questionCommentEntity = new QuestionCommentEntity();
        questionCommentEntity.setQuestionId(questionId);
        questionCommentEntity.setContent(content);
        questionCommentEntity.setCommenter(userEntity.getId());
        questionCommentEntity.setCommentTime(new Timestamp(System.currentTimeMillis()));
        questionCommentRepository.save(questionCommentEntity);
        questionRepository.updateCommentCountBy(questionId, 1);
        return questionCommentEntity;
    }

    public void uncommentQuestion(UserEntity userEntity, int commentId) {
        QuestionCommentEntity questionCommentEntity = questionCommentRepository.findById(commentId).get();
        if (questionCommentEntity.getCommenter() != userEntity.getId()) {
            throw new RuntimeException("不能删除别人的评论");
        }
        questionRepository.updateCommentCountBy(questionCommentEntity.getQuestionId(), -1);
        questionCommentRepository.delete(questionCommentEntity);
    }


    public AnswerCommentEntity commentAnswer(UserEntity userEntity, int answerId, String content) {
        AnswerCommentEntity answerCommentEntity = new AnswerCommentEntity();
        answerCommentEntity.setAnswerId(answerId);
        answerCommentEntity.setContent(content);
        answerCommentEntity.setAnswerId(userEntity.getId());
        answerCommentEntity.setCommentTime(new Timestamp(System.currentTimeMillis()));
        answerCommentRepository.save(answerCommentEntity);
        answerRepository.updateCommentCountBy(answerId, 1);
        return answerCommentEntity;
    }

    public void uncommentAnswer(UserEntity userEntity, int commentId) {
        AnswerCommentEntity answerCommentEntity = answerCommentRepository.findById(commentId).get();
        if (answerCommentEntity.getCommenter() != userEntity.getId()) {
            throw new RuntimeException("不能删除别人的评论");
        }
        answerRepository.updateCommentCountBy(answerCommentEntity.getAnswerId(), -1);
        answerCommentRepository.delete(answerCommentEntity);
    }

}
