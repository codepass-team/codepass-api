package com.codepass.user.dao.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "question", schema = "codepass", catalog = "")
public class QuestionEntity {
    private int id;
    private String title;
    private String content;
    private UserEntity questioner;
    private Timestamp raiseTime;
    private String dockerId;
    private Integer likeCount;
    private Integer status;
    //    private UserEntity questionerUser;
    private List<AnswerEntity> answer;
    private Integer commentCount;
    private Integer collectCount;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne()
    @JoinColumn(name = "questioner", referencedColumnName = "id")
    // 本表的name字段引用另一个表中的referencedColumnName字段,
    // referencedColumnName默认为另一个表的主键
    public UserEntity getQuestioner() {
        return questioner;
    }

    public void setQuestioner(UserEntity questioner) {
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

    @OneToMany(mappedBy = "questionId", fetch = FetchType.EAGER)
    // 拥有mappedBy注解的实体类为关系被维护端
    // mappedBy中的是另一个实体中的属性
    public List<AnswerEntity> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerEntity> answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "comment_count")
    @ColumnDefault("0")
    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Basic
    @Column(name = "collect_count")
    @ColumnDefault("0")
    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }
}
