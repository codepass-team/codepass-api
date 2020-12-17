package com.codepass.user.dao;

import com.codepass.user.dao.entity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Integer> {
}
