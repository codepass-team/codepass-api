package com.codepass.user.dao;

import com.codepass.user.dao.entity.LikeAnswerEntity;
import com.codepass.user.dao.entity.LikeAnswerEntityPK;
import org.springframework.data.repository.CrudRepository;

public interface LikeAnswerRepository extends CustomRepository<LikeAnswerEntity, LikeAnswerEntityPK> {
}
