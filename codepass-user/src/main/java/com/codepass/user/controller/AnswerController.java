package com.codepass.user.controller;

import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.AnswerEntity;
import com.codepass.user.dao.entity.CollectAnswerEntity;
import com.codepass.user.dao.entity.QuestionEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.AnswerDTO;
import com.codepass.user.dto.PageChunk;
import com.codepass.user.service.AnswerService;
import com.codepass.user.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/answer")
@Tag(name = "Answer", description = "答案管理相关API")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionService questionService;

    @PostMapping("/create")
    @Operation(summary = "创建回答", description = "创建一个回答")
    @ApiResponse(responseCode = "200", description = "成功创建回答, 返回dockerId和该回答的id")
    public ResponseEntity<?> createAnswer(@Parameter(description = "问题Id") @RequestParam int questionId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
            AnswerEntity answerEntity = answerService.createAnswer(userEntity, questionId, userDetails.getPassword());
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", answerEntity);
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @PostMapping("/save/{answerId}")
    @Operation(summary = "临时保存回答", description = "临时保存一个回答")
    @ApiResponse(responseCode = "200", description = "成功保存回答")
    public ResponseEntity<?> saveAnswer(@Parameter(description = "回答Id") @PathVariable int answerId,
                                        @Parameter(description = "回答内容") @RequestParam String content) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        try {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", answerService.updateAnswer(userEntity, answerId, content, false));
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @PostMapping("/submit/{answerId}")
    @Operation(summary = "提交回答", description = "提交回答, 提交后不能再修改")
    @ApiResponse(responseCode = "200", description = "提交成功")
    public ResponseEntity<?> submitAnswer(@Parameter(description = "回答Id") @PathVariable int answerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        try {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
                put("data", answerService.updateAnswer(userEntity, answerId, null, true));
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{answerId}")
    @Operation(summary = "删除回答", description = "删除回答")
    @ApiResponse(responseCode = "200", description = "提交成功")
    public ResponseEntity<?> deleteAnswer(@Parameter(description = "回答Id") @PathVariable int answerId) {
        var answer = answerService.getAnswer(answerId);
        if (answer == null)
            throw new RuntimeException("No answerId!");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        if (userEntity.getId() != answer.getAnswerer().getId())
            if (userEntity.getIsAdmin() == 0)
                throw new RuntimeException("Not Administrator and not answerer!");
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

    @GetMapping("/listAll")
    @Operation(summary = "获取数据库里的所有回答", description = "获取数据库里的所有回答", tags = "Admin")
    public ResponseEntity<?> listAllAnswers(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(401).build();
        }
        Page<AnswerEntity> answerEntities = answerService.getAllAnswer(page);
        PageChunk answers = new PageChunk(answerEntities);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answers);
        }});
    }

    @GetMapping("/listMy")
    @Operation(summary = "获取我提出的回答", description = "获取我提出的所有回答")
    public ResponseEntity<?> listAnswer(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        List<AnswerEntity> answerEntities = answerService.getUserAnswer(userEntity, page);
        List<AnswerDTO> answerDTOs = new ArrayList<>();
        for (AnswerEntity a : answerEntities) {
            QuestionEntity q = questionService.getQuestion(a.getQuestionId());
            AnswerDTO answerDTO = new AnswerDTO(a, q);
            answerDTOs.add(answerDTO);
        }
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerDTOs);
        }});
    }

    @GetMapping("/listCollect")
    @Operation(summary = "获取我收藏的回答", description = "获取我收藏的所有回答")
    public ResponseEntity<?> listCollect(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        int userId = userEntity.getId();
        List<CollectAnswerEntity> likeAnswerEntities = answerService.getUserCollect(userId, page);
        List<AnswerDTO> answerDTOs = new ArrayList<>();
        for (CollectAnswerEntity la : likeAnswerEntities) {
            AnswerEntity a = answerService.getAnswer(la.getAnswerId());
            QuestionEntity q = questionService.getQuestion(a.getQuestionId());
            AnswerDTO answerDTO = new AnswerDTO(a, q);
            answerDTOs.add(answerDTO);
        }
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerDTOs);
        }});
    }

    @PostMapping("/like/{answerId}")
    @Operation(description = "点赞回答")
    public ResponseEntity<?> like(@PathVariable int answerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        answerService.likeAnswer(userEntity, answerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/unlike/{answerId}")
    @Operation(description = "取消点赞回答")
    public ResponseEntity<?> unlike(@PathVariable int answerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        answerService.unlikeAnswer(userEntity, answerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/collect/{answerId}")
    @Operation(description = "收藏回答")
    public ResponseEntity<?> collect(@PathVariable int answerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        answerService.collectAnswer(userEntity, answerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/uncollect/{answerId}")
    @Operation(description = "取消收藏回答")
    public ResponseEntity<?> uncollect(@PathVariable int answerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        answerService.uncollectAnswer(userEntity, answerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }
}
