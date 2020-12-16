package com.codepass.user.controller;

import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dto.QuestionDTO;
import com.codepass.user.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping("/question/create")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionDTO questionDTO) {
        QuestionEntity questionEntity = questionService.createQuestion(questionDTO.getTitle(), questionDTO.getContent());
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/question/delete")
    public ResponseEntity<?> deleteQuestion(@RequestParam Integer questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Ok");
    }
}
