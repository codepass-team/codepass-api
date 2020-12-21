package com.codepass.user.controller;

import com.codepass.user.service.DockerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/*
swagger文档注解教程:
https://www.dariawan.com/tutorials/spring/documenting-spring-boot-rest-api-springdoc-openapi-3/
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Hello", description = "测试API")
public class HelloController {
    @Autowired
    DockerService dockerService;

    @GetMapping("/hello1")
    @Operation(summary = "测试接口1", description = "测试用的接口1, 不需要鉴权就可以调用")
    @ApiResponse(responseCode = "200", description = "正常返回")
    public String hello1() {
        try {
            dockerService.mountDocker("4a479ddd", "123456");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hello World1!";
    }

    @GetMapping("/hello2")
    @Operation(summary = "测试接口2", description = "测试用的接口2, 需要鉴权才能调用")
    @ApiResponse(responseCode = "200", description = "正常返回")
    public String hello2() {
        return "Hello World2!";
    }
}


