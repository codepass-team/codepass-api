package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "collect_answer", schema = "codepass", catalog = "")
@IdClass(CollectAnswerEntityPK.class)
public class CollectAnswerEntity {
    private int user;
    private int answerId;
    private Timestamp collectTime;
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
        return user == that.user && answerId == that.answerId && Objects.equals(collectTime, that.collectTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, answerId, collectTime);
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
