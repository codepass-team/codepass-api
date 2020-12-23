package com.codepass.user.controller;

import com.codepass.user.dao.LikeQuestionRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.*;
import com.codepass.user.dto.AnswerDTO;
import com.codepass.user.dto.PageChunk;
import com.codepass.user.dto.QuestionPojoQuery;
import com.codepass.user.service.DockerService;
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
@RequestMapping("/api/docker")
@Tag(name = "Docker", description = "Docker管理相关API")
public class DockerController {
    @Autowired
    DockerService dockerService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/listAll")
    @Operation(summary = "获取数据库里的docker信息", description = "获取数据库里的所有回答")
    public ResponseEntity<?> listAllQuestions(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) throws RuntimeException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        if (userEntity.getIsAdmin() == 0)
            throw new RuntimeException("Not Administrator!");
        Page<DockerEntity> answerEntities = dockerService.getAllDocker(page);
        PageChunk dockers = new PageChunk(answerEntities);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", dockers);
        }});
    }
}
