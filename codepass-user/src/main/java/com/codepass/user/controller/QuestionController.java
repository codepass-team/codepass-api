package com.codepass.user.controller;

import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.service.QuestionService;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@Tag(name = "Question", description = "问题管理相关API")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @PostMapping("/")
    @Operation(summary = "创建问题", description = "创建一个新问题, 需要提供问题标题")
    public ResponseEntity<?> createQuestion(@Parameter(description = "问题标题") @RequestParam String title) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.getUserByNickname(userDetails.getUsername());
        QuestionEntity questionEntity = questionService.createQuestion(userEntity.getId(), title);
        return ResponseEntity.ok(questionEntity);
    }

    @PostMapping("/save/{questionId}")
    @Operation(summary = "临时保存一个问题", description = "临时保存一个问题")
    public ResponseEntity<?> saveQuestion(@Parameter(description = "问题Id") @PathVariable int questionId,
                                          @Parameter(description = "新的问题标题") @RequestParam(required = false) String title,
                                          @Parameter(description = "新的问题描述") @RequestParam(required = false) String content) {
        questionService.changeQuestion(questionId, title, content, false);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/submit/{questionId}")
    @Operation(summary = "提交问题", description = "提交一个问题, 提交后不能再次编辑")
    public ResponseEntity<?> submitQuestion(@Parameter(description = "问题Id") @PathVariable int questionId) {
        questionService.changeQuestion(questionId, null, null, true);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "删除问题", description = "删除一个问题")
    public ResponseEntity<?> deleteQuestion(@Parameter(description = "问题Id") @PathVariable int questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/searchRecommend")
    @Operation(summary = "搜索推荐", description = "搜索问题时, 获取搜索提示的接口")
    public ResponseEntity<?> recommendQuestion(@Parameter(description = "搜索关键词") @RequestParam String keywords) {
        return ResponseEntity.ok(questionService.suggestQuestion(keywords));
    }

    @GetMapping("/search")
    @Operation(summary = "搜索问题", description = "搜索问题")
    public ResponseEntity<?> searchQuestion(@Parameter(description = "搜索内容") @RequestParam String keywords,
                                            @Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(questionService.searchQuestion(keywords, page));
    }

    @GetMapping("/{questionId}")
    @Operation(summary = "搜索问题", description = "获取某个问题的信息, 包括问题标题, 描述等, 还包括该问题所有回答的信息. 回答中不包括编辑中(status=0)的回答, 但自己提的问题忽略")
    public ResponseEntity<?> getQuestion(@Parameter(description = "问题Id") @PathVariable int questionId) {
        return ResponseEntity.ok(questionService.getQuestion(questionId));
    }

    @GetMapping("/list")
    @Operation(summary = "搜索某个用户提出的问题", description = "获取某用户提出的所有问题")
    public ResponseEntity<?> listQuestion(@Parameter(description = "用户Id") @RequestParam int userId,
                                          @Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(questionService.getUserQuestion(userId, page));
    }

    @GetMapping("/listAll")
    @Operation(summary = "获取所有问题", description = "获取所有问题")
    public ResponseEntity<?> listAllQuestions(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(questionService.getAllQuestion(page));
    }

    @PostMapping("/follow/{questionId}")
    @Operation(description = "关注问题, 关注后, 问题有新的回答会收到通知")
    public ResponseEntity<?> follow(@PathVariable int questionId) {
        return ResponseEntity.ok("Not implemented");
    }
}
