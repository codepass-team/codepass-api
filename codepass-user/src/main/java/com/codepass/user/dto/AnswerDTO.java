package com.codepass.user.dto;

import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.QuestionEntity;

public class AnswerDTO extends AnswerEntity{
    private String questionContent;

    public AnswerDTO(AnswerEntity a,QuestionEntity q){
        this.setId(a.getId());
        this.setAnswerer(a.getAnswerer());
        this.setContent(a.getContent());
        this.setAnswerTime(a.getAnswerTime());
        this.setDockerId(a.getDockerId());
        this.setLikeCount(a.getLikeCount());
        this.setStatus(a.getStatus());
        this.setQuestionId(a.getQuestionId());
        this.setQuestionContent(q.getContent());
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
