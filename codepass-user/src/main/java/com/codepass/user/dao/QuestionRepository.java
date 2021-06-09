package com.codepass.user.dao;

import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends CustomRepository<QuestionEntity, Integer> {

    Page<QuestionEntity> findByTitleLike(String keywords, Pageable pageable);

    Page<QuestionEntity> findByQuestioner(UserEntity questioner, Pageable pageable);

    Page<QuestionEntity> findByTitleLikeOrContentLike(String keyword1, String keyword2, Pageable pageable);
    @Modifying
    @Query("update QuestionEntity set likeCount = likeCount + ?2 where id = ?1")
    int updateLikeBy(int id, int count);

    @Modifying
    @Query("update QuestionEntity set commentCount = commentCount + ?2 where id = ?1")
    int updateCommentCountBy(int questionId, int i);

    @Modifying
    @Query("update QuestionEntity set collectCount = collectCount + ?2 where id = ?1")
    void updateCollectCountBy(int questionId, int i);
}
