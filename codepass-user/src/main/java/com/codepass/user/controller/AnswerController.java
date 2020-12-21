package com.codepass.user.controller;

import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/answer")
@Tag(name = "Answer", description = "答案管理相关API")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    @Operation(summary = "创建回答", description = "创建一个回答")
    @ApiResponse(responseCode = "200", description = "成功创建回答, 返回dockerId和该回答的id")
    public ResponseEntity<?> createAnswer(@Parameter(description = "问题Id") @RequestParam int questionId,
                                          @Parameter(description = "回答内容") @RequestParam String content) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserEntity userEntity = userRepository.findByNickname(userDetails.getUsername());
            AnswerEntity answerEntity = answerService.createAnswer(userEntity.getId(), questionId, content);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", answerEntity);
            }});
        } catch (Exception e) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
            }});
        }
    }

    @PostMapping("/save/{answerId}")
    @Operation(summary = "临时保存回答", description = "临时保存一个回答")
    @ApiResponse(responseCode = "200", description = "成功保存回答")
    public ResponseEntity<?> saveAnswer(@Parameter(description = "回答Id") @PathVariable int answerId,
                                        @Parameter(description = "回答内容") @RequestParam String content) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerService.updateAnswer(answerId, content, false));
        }});
    }

    @PostMapping("/submit/{answerId}")
    @Operation(summary = "提交回答", description = "提交回答, 提交后不能再修改")
    @ApiResponse(responseCode = "200", description = "提交成功")
    public ResponseEntity<?> submitAnswer(@Parameter(description = "回答Id") @PathVariable int answerId) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("answerId", answerService.updateAnswer(answerId, null, true));
        }});
    }

    @DeleteMapping("/{answerId}")
    @Operation(summary = "删除回答", description = "删除回答")
    @ApiResponse(responseCode = "200", description = "提交成功")
    public ResponseEntity<?> deleteAnswer(@Parameter(description = "回答Id") @PathVariable int answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @GetMapping("/{answerId}")
    @Operation(summary = "获取回答", description = "获取回答")
    @ApiResponse(responseCode = "200", description = "提交成功")
    public ResponseEntity<?> getAnswer(@Parameter(description = "回答Id") @PathVariable int answerId) {
        AnswerEntity answerEntity = answerService.getAnswer(answerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerEntity);
        }});
    }
}
