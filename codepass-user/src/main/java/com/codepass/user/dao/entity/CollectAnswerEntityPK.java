package com.codepass.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CollectAnswerEntityPK implements Serializable {
    private int user;
    private int answerId;
    private int userId;

    @Column(name = "user")
    @Id
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Column(name = "answer_id")
    @Id
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectAnswerEntityPK that = (CollectAnswerEntityPK) o;
        return user == that.user && answerId == that.answerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, answerId);
    }

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
