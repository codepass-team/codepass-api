package com.codepass.user.dao;

import com.codepass.user.dao.entity.FollowQuestionEntity;
import com.codepass.user.dao.entity.FollowQuestionEntityPK;
import com.codepass.user.dao.entity.LikeAnswerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowQuestionRepository extends CustomRepository<FollowQuestionEntity, FollowQuestionEntityPK> {
    Page<FollowQuestionEntity> findByUserId(int userid, Pageable pageable);

    boolean existsByUserIdAndQuestionId(int userid, int questionid);
}
