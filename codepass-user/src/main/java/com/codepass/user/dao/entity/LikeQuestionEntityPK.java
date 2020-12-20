package com.codepass.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class LikeQuestionEntityPK implements Serializable {
    private int userId;
    private int questionId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "question_id")
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

        LikeQuestionEntityPK that = (LikeQuestionEntityPK) o;

        if (userId != that.userId) return false;
        if (questionId != that.questionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + questionId;
        return result;
    }
}
