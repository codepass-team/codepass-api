package com.codepass.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment", description = "评论管理相关API")
public class CommentController {

    @GetMapping("/question/{questionId}")
    @Operation(description = "获取对一个问题的所有评论")
    public ResponseEntity<?> getQuestionComment(@PathVariable int questionId) {
        return ResponseEntity.ok("Not Implemented");
    }

    @PostMapping("/question/{questionId}")
    @Operation(description = "评论一个问题")
    public ResponseEntity<?> commentQuestion(@PathVariable int questionId, @RequestParam String content) {
        return ResponseEntity.ok("Not Implemented");
    }

    @DeleteMapping("/question/{questionId}")
    @Operation(description = "删除问题")
    public ResponseEntity<?> commentQuestion(@RequestParam String content) {
        return ResponseEntity.ok("Not Implemented");
    }

}
