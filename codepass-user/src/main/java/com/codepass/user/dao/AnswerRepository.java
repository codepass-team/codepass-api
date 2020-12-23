package com.codepass.user.dao;

import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends CustomRepository<AnswerEntity, Integer> {

    @Modifying
    @Query("update AnswerEntity set likeCount = likeCount + ?2 where id = ?1")
    int updateLikeBy(int id, int count);

    Page<AnswerEntity> findByAnswerer(UserEntity answerer, Pageable pageable);

    @Modifying
    @Query("update AnswerEntity set commentCount = commentCount + ?2 where id = ?1")
    int updateCommentCountBy(int answerId, int i);
}
