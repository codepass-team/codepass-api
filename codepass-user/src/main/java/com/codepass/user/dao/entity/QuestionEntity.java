package com.codepass.user.dao.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Basic
    @Column(name = "status")
    @ColumnDefault("0")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionEntity that = (QuestionEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (questioner != null ? !questioner.equals(that.questioner) : that.questioner != null) return false;
        if (raiseTime != null ? !raiseTime.equals(that.raiseTime) : that.raiseTime != null) return false;
        if (dockerId != null ? !dockerId.equals(that.dockerId) : that.dockerId != null) return false;
        if (likeCount != null ? !likeCount.equals(that.likeCount) : that.likeCount != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (questioner != null ? questioner.hashCode() : 0);
        result = 31 * result + (raiseTime != null ? raiseTime.hashCode() : 0);
        result = 31 * result + (dockerId != null ? dockerId.hashCode() : 0);
        result = 31 * result + (likeCount != null ? likeCount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
