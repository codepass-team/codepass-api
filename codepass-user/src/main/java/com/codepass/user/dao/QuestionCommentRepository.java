package com.codepass.user.dao;

import com.codepass.user.dao.entity.QuestionCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionCommentRepository extends CustomRepository<QuestionCommentEntity, Integer> {
    Page<QuestionCommentEntity> findByQuestionId(int questionId, Pageable pageable);
}
