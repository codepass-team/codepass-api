package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "question_comment", schema = "codepass", catalog = "")
public class QuestionCommentEntity {
    private int id;
    private int questionId;
    private int commenter;
    private String content;
    private Timestamp commentTime;

    @Id
    @Column(name = "id")
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
    @Column(name = "question_id")
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "commenter")
    public int getCommenter() {
        return commenter;
    }

    public void setCommenter(Integer commenter) {
        this.commenter = commenter;
    }

    public void setCommenter(int commenter) {
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
        return id == that.id && questionId == that.questionId && commenter == that.commenter && Objects.equals(content, that.content) && Objects.equals(commentTime, that.commentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionId, commenter, content, commentTime);
    }
}
