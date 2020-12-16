package com.codepass.persistent;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CollectAnswer", schema = "codepass", catalog = "")
@IdClass(CollectAnswerEntityPK.class)
public class CollectAnswerEntity {
    private int user;
    private int answerId;
    private Timestamp collectTime;

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
    @Column(name = "collectTime")
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
}
