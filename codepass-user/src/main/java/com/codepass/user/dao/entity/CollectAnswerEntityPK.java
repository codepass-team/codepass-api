package com.codepass.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CollectAnswerEntityPK implements Serializable {
    private int userId;
    private int answerId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

        if (userId != that.userId) return false;
        if (answerId != that.answerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + answerId;
        return result;
    }
}
