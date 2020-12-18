package com.codepass.user.controller;

import com.codepass.user.dto.QuestionVO;
import com.codepass.user.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@Tag(name = "Question", description = "问题管理相关API")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping("/")
    @Operation(summary = "创建问题", description = "创建一个新问题, 需要提供问题标题")
    public ResponseEntity<?> createQuestion(@RequestBody String title) {
//        QuestionEntity questionEntity = questionService.createQuestion(title);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/save/{questionId}")
    @Operation(summary = "临时保存一个问题", description = "临时保存一个问题")
    public ResponseEntity<?> saveQuestion(@PathVariable int questionId, @RequestBody String title) {
//        questionService.updateQuestion(questionId, title);
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/submit/{questionId}")
    @Operation(summary = "提交问题", description = "提交一个问题, 提交后不能再次编辑")
    public ResponseEntity<?> submitQuestion(@PathVariable int questionId) {
//        questionService.updateQuestion(0);
        return ResponseEntity.ok("Ok");
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "删除问题", description = "删除一个问题")
    public ResponseEntity<?> deleteQuestion(@RequestParam Integer questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/searchRecommend")
    @Operation(description = "搜索问题时, 获取搜索提示的接口")
    public ResponseEntity<?> recommendQuestion(@RequestParam String keywords) {
        return ResponseEntity.ok("Not implemented");
    }

    @GetMapping("/search")
    @Operation(description = "搜索问题")
    public ResponseEntity<?> searchQuestion(@RequestParam String keywords, @RequestParam int limit) {
        return ResponseEntity.ok("Not implemented");
    }

    @GetMapping("/{questionId}")
    @Operation(description = "获取某个问题的信息, 包括问题标题, 描述等, 还包括该问题所有回答的信息. 回答中不包括编辑中(status=0)的回答, 但自己提的问题忽略")
    public ResponseEntity<?> getQuestion(@PathVariable int questionId) {
        QuestionVO questionVO = new QuestionVO();
        return ResponseEntity.ok("Not implemented");
    }

    @GetMapping("/list")
    @Operation(description = "获取某用户提出的所有问题")
    public ResponseEntity<?> listQuestion(@RequestParam int userId) {
        return ResponseEntity.ok("Not implemented");
    }

    @GetMapping("/listAll")
    @Operation(description = "获取提出的所有问题")
    public ResponseEntity<?> listAllQuestions() {
        return ResponseEntity.ok("Not implemented");
    }

    @PostMapping("/follow/{questionId}")
    @Operation(description = "关注问题, 关注后, 问题有新的回答会收到通知")
    public ResponseEntity<?> follow(@PathVariable int questionId) {
        return ResponseEntity.ok("Not implemented");
    }
}
