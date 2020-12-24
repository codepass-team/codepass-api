package com.codepass.user.dao;

import com.codepass.user.dao.entity.LikeAnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntityPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeAnswerRepository extends CustomRepository<LikeAnswerEntity, LikeAnswerEntityPK> {
    Page<LikeAnswerEntity> findByUserId(int userid, Pageable pageable);

    boolean existsByUserIdAndAnswerId(int userid, int answerid);
}
