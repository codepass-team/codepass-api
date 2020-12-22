package com.codepass.user.dao;

import com.codepass.user.dao.entity.AnswerCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerCommentRepository extends CustomRepository<AnswerCommentEntity, Integer> {
    Page<AnswerCommentEntity> findByAnswerId(int answerId, Pageable pageable);
}
