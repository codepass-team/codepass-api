package com.codepass.user.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FollowQuestionEntityPK implements Serializable {
    private int user;
    private int questionId;

    @Column(name = "user")
    @Id
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Column(name = "questionID")
    @Id
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowQuestionEntityPK that = (FollowQuestionEntityPK) o;
        return user == that.user && questionId == that.questionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, questionId);
    }
}
