package com.codepass.user.dao.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "question", schema = "codepass", catalog = "")
public class QuestionEntity {
    private int id;
    private String title;
    private String content;
    private Integer questioner;
    private Timestamp raiseTime;
    private String dockerId;
    private Integer likeCount;
    private Integer status;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "raise_time")
    public Timestamp getRaiseTime() {
        return raiseTime;
    }

    public void setRaiseTime(Timestamp raiseTime) {
        this.raiseTime = raiseTime;
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
        QuestionEntity that = (QuestionEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(questioner, that.questioner) && Objects.equals(raiseTime, that.raiseTime) && Objects.equals(dockerId, that.dockerId) && Objects.equals(likeCount, that.likeCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, questioner, raiseTime, dockerId, likeCount);
    }

    @Basic
    @Column(name = "status")
    @ColumnDefault("0")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
