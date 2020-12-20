package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "like_answer", schema = "codepass", catalog = "")
@IdClass(LikeAnswerEntityPK.class)
public class LikeAnswerEntity {
    private int user;
    private int answerId;
    private Timestamp likeTime;
    private int userId;

    @Basic
    @Id
    @Column(name = "user")
    public int getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Id
    @Column(name = "answer_id")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
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
        return user == that.user && answerId == that.answerId && Objects.equals(likeTime, that.likeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, answerId, likeTime);
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
