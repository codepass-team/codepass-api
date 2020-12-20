package com.codepass.user.controller;

import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dto.AnswerCreateParam;
import com.codepass.user.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/answer")
@Tag(name = "Answer", description = "答案管理相关API")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping("/create")
    @Operation(description = "创建一个回答")
    @ApiResponse(responseCode = "200", description = "成功创建回答, 返回dockerId和该回答的id")
    public ResponseEntity<?> createAnswer(@RequestBody AnswerCreateParam answerCreateParam) {
        int userId = 0;
        int questionId = answerCreateParam.getQuestionId();
        String content = answerCreateParam.getContent();
        AnswerEntity answerEntity = answerService.createAnswer(userId, questionId, content);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerEntity);
        }});
    }

    @PostMapping("/save")
    @Operation(description = "临时保存一个回答")
    @ApiResponse(responseCode = "200", description = "成功保存回答")
    public ResponseEntity<?> createAnswer(@Parameter(description = "回答Id") @RequestParam int answerId,
                                          @Parameter(description = "回答内容") @RequestParam String content) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerService.updateAnswer(answerId, content));
        }});
    }

    @PostMapping("/submit")
    @Operation(description = "提交回答, 提交后不能再修改")
    @ApiResponse(responseCode = "200", description = "提交成功")
    public ResponseEntity<?> submitAnswer(@Parameter(description = "回答Id") @RequestParam int answerId,
                                          @Parameter(description = "回答内容") @RequestParam String content) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("answerId", answerService.updateAnswer(answerId, content));
        }});
    }
}
