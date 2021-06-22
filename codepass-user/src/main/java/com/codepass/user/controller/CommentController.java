package com.codepass.user.controller;

import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.PageChunk;
import com.codepass.user.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment", description = "评论管理相关API")
public class CommentController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{questionId}")
    @Operation(summary = "获取问题评论", description = "获取对一个问题的所有评论")
    public ResponseEntity<?> getQuestionComment(@Parameter(description = "问题Id") @PathVariable int questionId,
                                                @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", new PageChunk(commentService.getQuestionCommentById(questionId, page)));
        }});
    }

    @PostMapping("/question/{questionId}")
    @Operation(summary = "创建问题评论", description = "评论一个问题")
    public ResponseEntity<?> commentQuestion(@Parameter(description = "问题Id") @PathVariable int questionId,
                                             @Parameter(description = "评论内容") @RequestParam String content) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", commentService.commentQuestion(userEntity, questionId, content));
        }});
    }

    @DeleteMapping("/question/{commentId}")
    @Operation(summary = "删除问题评论", description = "删除问题评论")
    public ResponseEntity<?> deleteQuestionComment(@Parameter(description = "评论Id") @PathVariable int commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        try {
            commentService.uncommentQuestion(userEntity, commentId);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
            }});
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @GetMapping("/answer/{answerId}")
    @Operation(summary = "获取回答评论", description = "获取对一个回答的所有评论")
    public ResponseEntity<?> getAnswerComment(@Parameter(description = "回答Id") @PathVariable int answerId,
                                              @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", new PageChunk(commentService.getAnswerCommentById(answerId, page)));
        }});
    }

    @PostMapping("/answer/{answerId}")
    @Operation(summary = "创建回答评论", description = "评论一个回答")
    public ResponseEntity<?> commentAnswer(@Parameter(description = "回答Id") @PathVariable int answerId,
                                           @Parameter(description = "评论内容") @RequestParam String content) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", commentService.commentAnswer(userEntity, answerId, content));
        }});
    }

    @DeleteMapping("/answer/{commentId}")
    @Operation(summary = "删除回答评论", description = "删除回答评论")
    public ResponseEntity<?> commentAnswer(@Parameter(description = "评论Id") @PathVariable int commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        try {
            commentService.uncommentAnswer(userEntity, commentId);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
            }});
        } catch (RuntimeException e) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }
}
