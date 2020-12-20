package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "follow_question", schema = "codepass", catalog = "")
@IdClass(FollowQuestionEntityPK.class)
public class FollowQuestionEntity {
    private int userId;
    private int questionId;
    private Timestamp followTime;

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

        if (userId != that.userId) return false;
        if (questionId != that.questionId) return false;
        if (followTime != null ? !followTime.equals(that.followTime) : that.followTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + questionId;
        result = 31 * result + (followTime != null ? followTime.hashCode() : 0);
        return result;
    }
}
