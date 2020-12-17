package com.codepass.user.dto;

import java.util.List;

public class QuestionVO {
    private int questionId;
    private int userId;
    private String title;
    private String description;
    private String dockerId;
    private List<AnswerVO> answer;

}
