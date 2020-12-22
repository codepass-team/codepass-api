package com.codepass.user.dao;

import com.codepass.user.dao.entity.CollectAnswerEntity;
import com.codepass.user.dao.entity.CollectAnswerEntityPK;
import com.codepass.user.dao.entity.LikeAnswerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectAnswerRepository extends CustomRepository<CollectAnswerEntity, CollectAnswerEntityPK> {
    Page<CollectAnswerEntity> findByUserId(int userid, Pageable pageable);
}
