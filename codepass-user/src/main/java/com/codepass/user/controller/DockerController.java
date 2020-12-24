package com.codepass.user.controller;

import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.DockerEntity;
import com.codepass.user.dto.PageChunk;
import com.codepass.user.service.DockerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/docker")
@Tag(name = "Docker", description = "Docker管理相关API")
public class DockerController {
    @Autowired
    DockerService dockerService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/listAll")
    @Operation(summary = "获取数据库里的docker信息", description = "获取数据库里的所有回答", tags = "Admin")
    public ResponseEntity<?> listAllQuestions(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin")))
            throw new RuntimeException("Not Administrator!");
        Page<DockerEntity> answerEntities = dockerService.getAllDocker(page);
        PageChunk dockers = new PageChunk(answerEntities);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", dockers);
        }});
    }

    @DeleteMapping("/{dockerId}")
    @Operation(summary = "删除docker容器", description = "删除一个docker容器", tags = "Admin")
    public ResponseEntity<?> deleteQuestion(@Parameter(description = "Docker Id") @PathVariable String dockerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin")))
            throw new RuntimeException("Not Administrator!");
        dockerService.deleteDocker(dockerId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }

    @PostMapping("/start/{dockerId}")
    @Operation(summary = "启动docker容器", description = "启动一个docker容器", tags = "Admin")
    public ResponseEntity<?> startDocker(@Parameter(description = "Docker Id") @PathVariable String dockerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("admin")))
            throw new RuntimeException("Not Administrator!");
        try {
            dockerService.mountDocker(dockerId, "123456");
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "ok");
            }});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("status", "bad");
                put("data", e.getMessage());
            }});
        }
    }
}
