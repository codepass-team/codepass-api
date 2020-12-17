package com.codepass.user.controller;

import com.codepass.user.config.JwtTokenUtil;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.JwtResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    @ApiResponse(responseCode = "200", description = "登录成功", content = @Content(schema = @Schema(implementation = String.class, description = "JWT字符串")))
    public ResponseEntity<?> login(@Parameter(description = "用户邮箱") @RequestBody String email,
                                   @Parameter(description = "用户密码") @RequestBody String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (DisabledException e) {
            throw new Exception("Account disabled", e);
        } catch (LockedException e) {
            throw new Exception("Account locked", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);

        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public ResponseEntity<?> register(@Parameter(description = "用户邮箱") @RequestBody String email,
                                      @Parameter(description = "用户密码") @RequestBody String password) {
        UserEntity userEntity = userService.createNewUser(email, password);
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/logout")
    @Operation(summary = "用户注销", description = "用户注销接口")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }
}