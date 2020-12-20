package com.codepass.user.dao;

import com.codepass.user.dao.entity.AnswerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends CustomRepository<AnswerEntity, Integer> {

    @Modifying
    @Query("update AnswerEntity set likeCount = likeCount + ?2 where id = ?1")
    public int updateLikeBy(int id, int count);

}
