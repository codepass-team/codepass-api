package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "like_question", schema = "codepass", catalog = "")
@IdClass(LikeQuestionEntityPK.class)
public class LikeQuestionEntity {
    private int user;
    private int questionId;
    private Timestamp likeTime;
    private int userId;

    @Id
    @Column(name = "user")
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
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
        return user == that.user && questionId == that.questionId && Objects.equals(likeTime, that.likeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, questionId, likeTime);
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
