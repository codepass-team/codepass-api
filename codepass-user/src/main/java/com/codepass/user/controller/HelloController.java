package com.codepass.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello1")
    public String hello1() {
        return "Hello World1!";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "Hello World2!";
    }
}


