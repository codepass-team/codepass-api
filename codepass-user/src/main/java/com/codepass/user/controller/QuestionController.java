package com.codepass.user.controller;

import com.codepass.user.dao.LikeQuestionRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.*;
import com.codepass.user.dto.AnswerDTO;
import com.codepass.user.dto.PageChunk;
import com.codepass.user.dto.QuestionPojoQuery;
import com.codepass.user.service.QuestionService;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/question")
@Tag(name = "Question", description = "问题管理相关API")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    @Operation(summary = "创建问题", description = "创建一个新问题, 需要提供问题标题")
    public ResponseEntity<?> createQuestion(@Parameter(description = "问题标题") @RequestParam String title) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.getUserByUsername(userDetails.getUsername());
        try {
            QuestionEntity questionEntity = questionService.createQuestion(userEntity, title);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", questionEntity);
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @PostMapping("/save/{questionId}")
    @Operation(summary = "临时保存一个问题", description = "临时保存一个问题")
    public ResponseEntity<?> saveQuestion(@Parameter(description = "问题Id") @PathVariable int questionId,
                                          @Parameter(description = "新的问题标题") @RequestParam(required = false) String title,
                                          @Parameter(description = "新的问题描述") @RequestParam(required = false) String content) {
        try {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", questionService.changeQuestion(questionId, title, content, false));
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @PostMapping("/submit/{questionId}")
    @Operation(summary = "提交问题", description = "提交一个问题, 提交后不能再次编辑")
    public ResponseEntity<?> submitQuestion(@Parameter(description = "问题Id") @PathVariable int questionId) {
        try {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", questionService.changeQuestion(questionId, null, null, true));
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{questionId}")
    @Operation(summary = "删除问题", description = "删除一个问题")
    public ResponseEntity<?> deleteQuestion(@Parameter(description = "问题Id") @PathVariable int questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @GetMapping("/searchRecommend")
    @Operation(summary = "搜索推荐", description = "搜索问题时, 获取搜索提示的接口")
    public ResponseEntity<?> recommendQuestion(@Parameter(description = "搜索关键词") @RequestParam String keywords,
                                               @Parameter(description = "最多返回数据条数") @RequestParam(defaultValue = "10") int limits) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questionService.suggestQuestion("%" + keywords + "%", limits));
        }});
    }

    @GetMapping("/search")
    @Operation(summary = "搜索问题", description = "搜索问题")
    public ResponseEntity<?> searchQuestion(@Parameter(description = "搜索内容") @RequestParam String keywords,
                                            @Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questionService.searchQuestion("%" + keywords + "%", page));
        }});
    }

    @GetMapping("/{questionId}")
    @Operation(summary = "获取问题", description = "获取某个问题的信息, 包括问题标题, 描述等, 还包括该问题所有回答的信息. 回答中不包括编辑中(status=0)的回答, 但自己提的问题忽略")
    public ResponseEntity<?> getQuestion(@Parameter(description = "问题Id") @PathVariable int questionId) {
        QuestionPojoQuery question = new QuestionPojoQuery(questionService.getQuestion(questionId));
        question.setUlike(questionService.checkLike(questionId));
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", question);
        }});
    }

    @GetMapping("/listMy")
    @Operation(summary = "获取我提出的问题", description = "获取我提出的所有问题")
    public ResponseEntity<?> listQuestion(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questionService.getUserQuestion(userEntity, page));
        }});
    }

    @GetMapping("/list")
    @Operation(summary = "获取某个用户提出的问题", description = "获取某用户提出的所有问题, 按照时间顺序排序")
    public ResponseEntity<?> listQuestion(@Parameter(description = "用户Id") @RequestParam int userId,
                                          @Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        UserEntity userEntity = userService.getUserById(userId);
        List<QuestionEntity> questionEntities = questionService.getUserQuestion(userEntity, page);
        questionEntities.sort((o1, o2) -> (o2.getRaiseTime().compareTo(o1.getRaiseTime())));
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questionEntities);
        }});
    }

    @GetMapping("/listAll")
    @Operation(summary = "获取所有问题", description = "获取所有问题")
    public ResponseEntity<?> listAllQuestions(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        Page<QuestionEntity> questionEntities = questionService.getAllQuestion(page);
        PageChunk questions = new PageChunk(questionEntities);
//        List<QuestionPojo> questionPojos = new ArrayList<>();
//        for (QuestionEntity q : questionEntities) {
//            UserEntity u = userService.getUserById(q.getQuestioner());
//            QuestionPojo questionPojo = new QuestionPojo(q, u);
//            questionPojos.add(questionPojo);
//        }
        //按时间从降序排
//        questionPojos.sort((o1, o2) -> (o2.getRaiseTime().compareTo(o1.getRaiseTime())));
//        questions.setContent(questionPojos);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questions);
        }});
    }

    @GetMapping("/listFollow")
    @Operation(summary = "获取我关注的问题", description = "获取我关注的所有问题")
    public ResponseEntity<?> listFollow(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        int userId = userEntity.getId();
        List<FollowQuestionEntity> followQuestionEntities = questionService.getUserFollow(userId, page);
        List<QuestionEntity> questionEntityList = new ArrayList<>();
        for (FollowQuestionEntity fq : followQuestionEntities) {
            QuestionEntity a = questionService.getQuestion(fq.getQuestionId());
            questionEntityList.add(a);
        }
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questionEntityList);
        }});
    }

    @PostMapping("/follow/{questionId}")
    @Operation(description = "关注问题")
    public ResponseEntity<?> follow(@PathVariable int questionId) {
        questionService.followQuestion(questionId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/unfollow/{questionId}")
    @Operation(description = "取消关注问题")
    public ResponseEntity<?> unfollow(@PathVariable int questionId) {
        questionService.unfollowQuestion(questionId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/like/{questionId}")
    @Operation(description = "点赞问题")
    public ResponseEntity<?> like(@PathVariable int questionId) {
        questionService.likeQuestion(questionId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/unlike/{questionId}")
    @Operation(description = "取消点赞问题")
    public ResponseEntity<?> unlike(@PathVariable int questionId) {
        questionService.unlikeQuestion(questionId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }
}
