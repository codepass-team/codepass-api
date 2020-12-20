package com.codepass.user.dao;

import com.codepass.user.dao.entity.QuestionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

    Page<QuestionEntity> findByTitleLike(String keywords, Pageable pageable);

    Page<QuestionEntity> findByQuestioner(int questionerId, Pageable pageable);

    // @Query("select question.id,title,content,questioner,raise_time,docker_id,like_count,status,email from question,user where question.questioner = user.id")
    // public List<QuestionPojo> findAllWithUsername(Pageable pageable);
}
