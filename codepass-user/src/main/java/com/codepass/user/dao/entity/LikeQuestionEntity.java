package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "like_question", schema = "codepass", catalog = "")
@IdClass(LikeQuestionEntityPK.class)
public class LikeQuestionEntity {
    private int userId;
    private int questionId;
    private Timestamp likeTime;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "like_time")
    public Timestamp getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Timestamp likeTime) {
        this.likeTime = likeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikeQuestionEntity that = (LikeQuestionEntity) o;

        if (userId != that.userId) return false;
        if (questionId != that.questionId) return false;
        if (likeTime != null ? !likeTime.equals(that.likeTime) : that.likeTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + questionId;
        result = 31 * result + (likeTime != null ? likeTime.hashCode() : 0);
        return result;
    }
}
