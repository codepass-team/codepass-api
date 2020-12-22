package com.codepass.user.dao;

import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntityPK;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface LikeAnswerRepository extends CustomRepository<LikeAnswerEntity, LikeAnswerEntityPK> {
    Page<LikeAnswerEntity> findByUserId(int userid, Pageable pageable);
}
