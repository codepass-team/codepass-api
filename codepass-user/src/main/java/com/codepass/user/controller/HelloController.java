package com.codepass.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello1")
    @Operation(description = "测试用的接口1, 不需要鉴权就可以调用")
    @ApiResponse(responseCode = "200", description = "正常返回")
    public String hello1() {
        return "Hello World1!";
    }

    @GetMapping("/hello2")
    @Operation(description = "测试用的接口2, 需要鉴权才能调用")
    @ApiResponse(responseCode = "200", description = "正常返回")
    @ApiResponse(responseCode = "401", description = "没有登录")
    public String hello2() {
        return "Hello World2!";
    }
}


