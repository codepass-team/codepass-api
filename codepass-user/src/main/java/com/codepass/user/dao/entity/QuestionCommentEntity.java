package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "question_comment", schema = "codepass", catalog = "")
public class QuestionCommentEntity {
    private int id;
    private int questionId;
    private UserEntity commenter;
    private String content;
    private Timestamp commentTime;

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
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    //    @Basic
//    @Column(name = "commenter")
    @OneToOne()
    @JoinColumn(name = "commenter")//, referencedColumnName = "id")
    public UserEntity getCommenter() {
        return commenter;
    }

    public void setCommenter(UserEntity commenter) {
        this.commenter = commenter;
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
    @Column(name = "comment_time")
    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionCommentEntity that = (QuestionCommentEntity) o;

        if (id != that.id) return false;
        if (questionId != that.questionId) return false;
        if (commenter != that.commenter) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (commentTime != null ? !commentTime.equals(that.commentTime) : that.commentTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + questionId;
        result = 31 * result + commenter.getId();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (commentTime != null ? commentTime.hashCode() : 0);
        return result;
    }

//    private UserEntity user;
}
