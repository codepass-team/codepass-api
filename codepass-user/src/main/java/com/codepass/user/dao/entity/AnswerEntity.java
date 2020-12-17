package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "answer", schema = "codepass", catalog = "")
public class AnswerEntity {
    private int id;
    private Integer answerer;
    private String content;
    private Timestamp answerTime;
    private String dockerId;
    private Integer likeCount;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "answerer")
    public Integer getAnswerer() {
        return answerer;
    }

    public void setAnswerer(Integer answerer) {
        this.answerer = answerer;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "answer_time")
    public Timestamp getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Timestamp answerTime) {
        this.answerTime = answerTime;
    }

    @Basic
    @Column(name = "docker_id")
    public String getDockerId() {
        return dockerId;
    }

    public void setDockerId(String dockerId) {
        this.dockerId = dockerId;
    }

    @Basic
    @Column(name = "like_count")
    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerEntity that = (AnswerEntity) o;
        return id == that.id && Objects.equals(answerer, that.answerer) && Objects.equals(content, that.content) && Objects.equals(answerTime, that.answerTime) && Objects.equals(dockerId, that.dockerId) && Objects.equals(likeCount, that.likeCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answerer, content, answerTime, dockerId, likeCount);
    }
}
