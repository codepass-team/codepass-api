package com.codepass.user.controller;

import com.codepass.user.config.JwtTokenUtil;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.JwtResponse;
import com.codepass.user.dto.UserLoginParam;
import com.codepass.user.dto.UserRegisterParam;
import com.codepass.user.service.JwtUserDetailsService;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
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
    @Operation(description = "用户登录接口")
    public ResponseEntity<?> login(@RequestBody UserLoginParam user) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getEmail());

        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    @Operation(description = "用户注册接口")
    public ResponseEntity<?> register(@RequestBody UserRegisterParam user) throws Exception {
        UserEntity userEntity = userService.createNewUser(user);
        return ResponseEntity.ok(userEntity);
    }
}