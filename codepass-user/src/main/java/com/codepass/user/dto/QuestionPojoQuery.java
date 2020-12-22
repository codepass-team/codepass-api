package com.codepass.user.dto;

import com.codepass.user.dao.entity.QuestionEntity;

public class QuestionPojoQuery extends QuestionEntity {
    private int ulike;

    public QuestionPojoQuery(QuestionEntity q) {
        this.setId(q.getId());
        this.setTitle(q.getTitle());
        this.setContent(q.getContent());
        this.setQuestioner(q.getQuestioner());
        this.setRaiseTime(q.getRaiseTime());
        this.setDockerId(q.getDockerId());
        this.setLikeCount(q.getLikeCount());
        this.setStatus(q.getStatus());
        this.setAnswer(q.getAnswer());
    }

    public int getUlike() {
        return ulike;
    }

    public void setUlike(int ulike) {
        this.ulike = ulike;
    }
}
