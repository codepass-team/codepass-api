package com.codepass.user.dto;

import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dao.entity.UserEntity;

public class QuestionPojo extends QuestionEntity{
    private String nickname;

    public QuestionPojo(QuestionEntity q,UserEntity u){
        this.setId(q.getId());
        this.setTitle(q.getTitle());
        this.setContent(q.getContent());
        this.setQuestioner(q.getQuestioner());
        this.setRaiseTime(q.getRaiseTime());
        this.setDockerId(q.getDockerId());
        this.setLikeCount(q.getLikeCount());
        this.setStatus(q.getStatus());
        this.setNickname(u.getUsername());
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
