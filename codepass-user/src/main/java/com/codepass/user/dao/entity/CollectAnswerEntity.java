package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "collect_answer", schema = "codepass", catalog = "")
@IdClass(CollectAnswerEntityPK.class)
public class CollectAnswerEntity {
    private int userId;
    private int answerId;
    private Timestamp collectTime;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "answer_id")
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Basic
    @Column(name = "collect_time")
    public Timestamp getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Timestamp collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollectAnswerEntity that = (CollectAnswerEntity) o;

        if (userId != that.userId) return false;
        if (answerId != that.answerId) return false;
        if (collectTime != null ? !collectTime.equals(that.collectTime) : that.collectTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + answerId;
        result = 31 * result + (collectTime != null ? collectTime.hashCode() : 0);
        return result;
    }
}
