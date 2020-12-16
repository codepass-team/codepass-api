package com.codepass.persistent;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "LikeQuestion", schema = "codepass", catalog = "")
@IdClass(LikeQuestionEntityPK.class)
public class LikeQuestionEntity {
    private int user;
    private int questionId;
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
    @Column(name = "questionID")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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
        LikeQuestionEntity that = (LikeQuestionEntity) o;
        return user == that.user && questionId == that.questionId && Objects.equals(likeTime, that.likeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, questionId, likeTime);
    }
}
