package com.codepass.user.dao;

import com.codepass.user.dao.entity.AnswerEntity;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<AnswerEntity, Integer> {
}
