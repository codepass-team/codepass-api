package com.codepass.user.dto;

import com.codepass.user.dao.entity.QuestionEntity;

public class QuestionPojo extends QuestionEntity{
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
