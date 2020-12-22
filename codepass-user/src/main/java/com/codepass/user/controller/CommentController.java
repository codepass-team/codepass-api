package com.codepass.user.controller;

import com.codepass.user.dao.AnswerCommentRepository;
import com.codepass.user.dao.QuestionCommentRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.AnswerCommentEntity;
import com.codepass.user.dao.entity.QuestionCommentEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.PageChunk;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment", description = "评论管理相关API")
public class CommentController {
    @Autowired
    AnswerCommentRepository answerCommentRepository;
    @Autowired
    QuestionCommentRepository questionCommentRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/question/{questionId}")
    @Operation(summary = "获取问题评论", description = "获取对一个问题的所有评论")
    public ResponseEntity<?> getQuestionComment(@Parameter(description = "问题Id") @PathVariable int questionId,
                                                @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page) {
        Page<QuestionCommentEntity> questionCommentEntities = questionCommentRepository.findByQuestionId(questionId, PageRequest.of(page, 10));
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", new PageChunk(questionCommentEntities));
        }});
    }

    @PostMapping("/question/{questionId}")
    @Operation(summary = "创建问题评论", description = "评论一个问题")
    public ResponseEntity<?> commentQuestion(@Parameter(description = "问题Id") @PathVariable int questionId,
                                             @Parameter(description = "评论内容") @RequestParam String content) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByNickname(userDetails.getUsername());
        QuestionCommentEntity questionCommentEntity = new QuestionCommentEntity();
        questionCommentEntity.setQuestionId(questionId);
        questionCommentEntity.setContent(content);
        questionCommentEntity.setCommenter(userEntity.getId());
        questionCommentEntity.setCommentTime(new Timestamp(System.currentTimeMillis()));
        questionCommentRepository.save(questionCommentEntity);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", questionCommentEntity);
        }});
    }

    @DeleteMapping("/question/{commentId}")
    @Operation(summary = "删除问题评论", description = "删除问题评论")
    public ResponseEntity<?> deleteQuestionComment(@Parameter(description = "评论Id") @PathVariable int commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByNickname(userDetails.getUsername());
        QuestionCommentEntity questionCommentEntity = questionCommentRepository.findById(commentId).get();
        if (questionCommentEntity.getCommenter() != userEntity.getId()) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", "不能删除别人的评论");
            }});
        }
        questionCommentRepository.delete(questionCommentEntity);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }


    @GetMapping("/answer/{answerId}")
    @Operation(summary = "获取回答评论", description = "获取对一个回答的所有评论")
    public ResponseEntity<?> getAnswerComment(@Parameter(description = "回答Id") @PathVariable int answerId,
                                              @Parameter(description = "页码") @RequestParam(defaultValue = "0") int page) {
        Page<AnswerCommentEntity> answerCommentEntities = answerCommentRepository.findByAnswerId(answerId, PageRequest.of(page, 10));
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", new PageChunk(answerCommentEntities));
        }});
    }

    @PostMapping("/answer/{answerId}")
    @Operation(summary = "创建回答评论", description = "评论一个回答")
    public ResponseEntity<?> commentAnswer(@Parameter(description = "回答Id") @PathVariable int answerId,
                                           @Parameter(description = "评论内容") @RequestParam String content) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByNickname(userDetails.getUsername());
        AnswerCommentEntity answerCommentEntity = new AnswerCommentEntity();
        answerCommentEntity.setAnswerId(answerId);
        answerCommentEntity.setContent(content);
        answerCommentEntity.setAnswerId(userEntity.getId());
        answerCommentEntity.setCommentTime(new Timestamp(System.currentTimeMillis()));
        answerCommentRepository.save(answerCommentEntity);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", answerCommentEntity);
        }});
    }

    @DeleteMapping("/answer/{commentId}")
    @Operation(summary = "删除回答评论", description = "删除回答评论")
    public ResponseEntity<?> commentAnswer(@Parameter(description = "评论Id") @PathVariable int commentId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByNickname(userDetails.getUsername());
        AnswerCommentEntity answerCommentEntity = answerCommentRepository.findById(commentId).get();
        if (answerCommentEntity.getCommenter() != userEntity.getId()) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", "不能删除别人的评论");
            }});
        }
        answerCommentRepository.delete(answerCommentEntity);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }


}
