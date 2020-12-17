package com.codepass.user.controller;

import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dto.AnswerCreateParam;
import com.codepass.user.dto.AnswerCreateVO;
import com.codepass.user.service.AnswerService;
import com.codepass.user.service.DockerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping("/create")
    @Operation(description = "创建一个回答")
    @ApiResponse(responseCode = "200", description = "成功创建回答, 返回url")
    public ResponseEntity<?> createAnswer(@RequestBody AnswerCreateParam answerCreateParam) {
        int userId = 0;
        int questionId = answerCreateParam.getQuestionId();
        String content = answerCreateParam.getContent();
        AnswerEntity answerEntity = answerService.createAnswer(userId, questionId, content);
        return ResponseEntity.ok(new AnswerCreateVO("Ok", answerEntity.getDockerId(), answerEntity.getId()));
    }
}
