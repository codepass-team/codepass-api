package com.codepass.user.controller;

import com.codepass.user.dao.DockerRepository;
import com.codepass.user.service.DockerService;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@Tag(name = "Misc", description = "杂项API")
public class MiscController {
    @Autowired
    DockerRepository dockerRepository;
    @Autowired
    DockerService dockerService;
    @Autowired
    UserService userService;

    @GetMapping("/api/docker/{dockerId}")
    @Operation(summary = "进入容器", description = "进入容器")
    public ResponseEntity<?> redirectToDocker(@Parameter(description = "容器Id") @PathVariable String dockerId,
                                              HttpServletRequest request) {
        String baseUri = request.getHeader("Host");
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, dockerService.getUri(dockerId, baseUri)).build();
    }


    @PostMapping("/api/reset/email")
    @Operation(summary = "发送密码重置邮件", description = "发送密码重置邮件")
    public ResponseEntity<?> sendEmail(@Parameter(description = "用户名") @RequestParam(required = true) String name) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", userService.sendEmail(name));
        }});
    }

    @PostMapping("/api/reset/password")
    @Operation(summary = "重置密码", description = "重置密码")
    public ResponseEntity<?> resetPassword(@Parameter(description = "用户名") @RequestParam(required = true) String name,
                                           @Parameter(description = "新密码") @RequestParam(required = true) String password,
                                           @Parameter(description = "验证码") @RequestParam(required = true) int captcha) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", userService.resetPassword(name, password, captcha));
        }});
    }
}
