package com.codepass.user.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Question", schema = "codepass", catalog = "")
public class QuestionEntity {
    private int id;
    private String title;
    private String content;
    private Integer questioner;
    private Timestamp raiseTime;
    private String dockerId;
    private Integer likeCount;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "questioner")
    public Integer getQuestioner() {
        return questioner;
    }

    public void setQuestioner(Integer questioner) {
        this.questioner = questioner;
    }

    @Basic
    @Column(name = "raiseTime")
    public Timestamp getRaiseTime() {
        return raiseTime;
    }

    public void setRaiseTime(Timestamp raiseTime) {
        this.raiseTime = raiseTime;
    }

    @Basic
    @Column(name = "dockerID")
    public String getDockerId() {
        return dockerId;
    }

    public void setDockerId(String dockerId) {
        this.dockerId = dockerId;
    }

    @Basic
    @Column(name = "likeCount")
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
        QuestionEntity that = (QuestionEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(questioner, that.questioner) && Objects.equals(raiseTime, that.raiseTime) && Objects.equals(dockerId, that.dockerId) && Objects.equals(likeCount, that.likeCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, questioner, raiseTime, dockerId, likeCount);
    }
}
