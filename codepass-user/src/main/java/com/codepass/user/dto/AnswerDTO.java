package com.codepass.user.dto;

import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dao.entity.UserEntity;

public class AnswerDTO extends AnswerEntity{
    private String questionTitle;
    private UserEntity questioner;

    public AnswerDTO(AnswerEntity a,QuestionEntity q){
        this.setId(a.getId());
        this.setAnswerer(a.getAnswerer());
        this.setContent(a.getContent());
        this.setAnswerTime(a.getAnswerTime());
        this.setDockerId(a.getDockerId());
        this.setLikeCount(a.getLikeCount());
        this.setStatus(a.getStatus());
        this.setQuestionId(a.getQuestionId());
        this.setQuestionTitle(q.getTitle());
        this.setQuestioner(q.getQuestioner());
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public UserEntity getQuestioner() {
        return questioner;
    }

    public void setQuestioner(UserEntity questioner) {
        this.questioner = questioner;
    }
}
