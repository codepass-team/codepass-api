package com.codepass.user.controller;

import com.codepass.user.config.JwtTokenUtil;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.service.JwtUserDetailsService;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth", description = "用户认证相关API")
public class JwtAuthenticationController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    @ApiResponse(responseCode = "200", description = "登录成功", content = @Content(schema = @Schema(implementation = String.class, description = "JWT字符串")))
    public ResponseEntity<?> login(@Parameter(description = "用户名") @RequestParam String username,
                                   @Parameter(description = "用户密码") @RequestParam String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("Account disabled", e);
        } catch (LockedException e) {
            throw new Exception("Account locked", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        String token = jwtTokenUtil.generateToken(userDetails);

        // 刷新token
        stringRedisTemplate.opsForValue().set(username, token);

        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", new HashMap<String, Object>() {{
                put("token", token);
            }});
        }});
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public ResponseEntity<?> register(@Parameter(description = "用户名") @RequestParam String username,
                                      @Parameter(description = "用户密码") @RequestParam String password) {
        UserEntity userEntity = userService.createNewUser(username, password);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", userEntity);
        }});
    }

    @PostMapping("/logout")
    @Operation(summary = "用户注销", description = "用户注销接口")
    public ResponseEntity<?> logout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userService.getUserByUsername(userDetails.getUsername());

        // 清除token
        stringRedisTemplate.delete(userEntity.getUsername());

        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }
}