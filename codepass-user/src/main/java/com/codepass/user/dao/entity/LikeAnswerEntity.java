package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "LikeAnswer", schema = "codepass", catalog = "")
@IdClass(LikeAnswerEntityPK.class)
public class LikeAnswerEntity {
    private int user;
    private int answerId;
    private Timestamp likeTime;

    @Id
    @Column(name = "user")
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Id
    @Column(name = "answerID")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Basic
    @Column(name = "likeTime")
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
}
