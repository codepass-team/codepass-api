package com.codepass.user.controller;

import com.codepass.user.service.DockerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello1")
    @Operation(summary = "测试接口1", description = "测试用的接口1, 不需要鉴权就可以调用")
    @ApiResponse(responseCode = "200", description = "正常返回")
    public String hello1(@RequestParam String cmds) {
        try {
            ProcessBuilder builder = new ProcessBuilder(cmds);
            builder.redirectErrorStream(true);
            for (String cmd : builder.command()) {
                logger.info(cmd);
            }
            Process process = builder.start();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                logger.info("Stdout: " + line);
            }
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


