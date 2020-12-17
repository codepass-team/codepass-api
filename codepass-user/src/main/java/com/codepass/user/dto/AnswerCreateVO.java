package com.codepass.user.dto;

public class AnswerCreateVO {
    private String status;
    private String dockerId;
    private int answerId;

    public AnswerCreateVO(String status, String dockerId, int answerId) {
        this.status = status;
        this.dockerId = dockerId;
        this.answerId = answerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDockerId() {
        return dockerId;
    }

    public void setDockerId(String dockerId) {
        this.dockerId = dockerId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
