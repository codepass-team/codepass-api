package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "follow_question", schema = "codepass", catalog = "")
@IdClass(FollowQuestionEntityPK.class)
public class FollowQuestionEntity {
    private int user;
    private int questionId;
    private Timestamp followTime;
    private int userId;

    @Id
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "follow_time")
    public Timestamp getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Timestamp followTime) {
        this.followTime = followTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowQuestionEntity that = (FollowQuestionEntity) o;
        return user == that.user && questionId == that.questionId && Objects.equals(followTime, that.followTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, questionId, followTime);
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
