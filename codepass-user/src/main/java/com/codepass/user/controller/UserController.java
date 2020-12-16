package com.codepass.user.controller;

import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class UserController {
    public static final String KEY_USER = "__USER__";

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @PostMapping("/follow/{user_id}")
    @Operation(description = "关注用户")
    public ResponseEntity<?> follow_user(@PathParam("user_id") int userId) {
        userService.followUser(userId, userId);
        return ResponseEntity.ok("Ok");
    }
}
