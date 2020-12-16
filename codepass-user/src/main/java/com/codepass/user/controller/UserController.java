package com.codepass.user.controller;

import com.codepass.user.entity.UserEntity;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserController {
    public static final String KEY_USER = "__USER__";

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

}
