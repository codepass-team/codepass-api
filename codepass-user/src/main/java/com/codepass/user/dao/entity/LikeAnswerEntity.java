package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "like_answer", schema = "codepass", catalog = "")
@IdClass(LikeAnswerEntityPK.class)
public class LikeAnswerEntity {
    private int userId;
    private int answerId;
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
    @Column(name = "answer_id")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

        LikeAnswerEntity that = (LikeAnswerEntity) o;

        if (userId != that.userId) return false;
        if (answerId != that.answerId) return false;
        if (likeTime != null ? !likeTime.equals(that.likeTime) : that.likeTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + answerId;
        result = 31 * result + (likeTime != null ? likeTime.hashCode() : 0);
        return result;
    }
}
