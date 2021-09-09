package com.nga.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//用来测试登录成功后能否被访问
@RestController
@RequestMapping("/")
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/fuck")
    public String fuck(){
        return "fuck";
    }

    @GetMapping("/login")
    public String login(){
        return "权限不足，请前往登录";
    }
}
