package com.wegrow.wegrow.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 创建管理员页面，我们需要对其进行保护，使其只能被管理员访问
@RequestMapping("/admin/api")
public class AdminController {

    @GetMapping("hello")
    public String hello() {
        return "hello, admin";
    }

}
