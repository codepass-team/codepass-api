package com.codepass.user.dao;

import com.codepass.user.dao.entity.AnswerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends CustomRepository<AnswerEntity, Integer> {

    @Modifying
    @Query("update AnswerEntity set likeCount = likeCount + ?2 where id = ?1")
    int updateLikeBy(int id, int count);

    Page<AnswerEntity> findByAnswerer(int answererId, Pageable pageable);
}
