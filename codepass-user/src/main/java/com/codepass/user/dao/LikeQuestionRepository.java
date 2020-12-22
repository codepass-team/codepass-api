package com.codepass.user.dao;

import com.codepass.user.dao.entity.LikeQuestionEntity;
import com.codepass.user.dao.entity.LikeQuestionEntityPK;

public interface LikeQuestionRepository extends CustomRepository<LikeQuestionEntity, LikeQuestionEntityPK> {

    boolean existsByUserIdAndQuestionId(int userid, int qid);
}
