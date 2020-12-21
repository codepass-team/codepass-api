package com.codepass.user.dao;

import com.codepass.user.dao.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository extends CustomRepository<QuestionEntity, Integer> {

    Page<QuestionEntity> findByTitleLike(String keywords, Pageable pageable);

    Page<QuestionEntity> findByQuestioner(int questionerId, Pageable pageable);

}
