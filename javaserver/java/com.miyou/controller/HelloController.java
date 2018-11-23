package com.miyou.controller;

import com.miyou.model.User;
import com.miyou.service.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @Autowired
    TestServiceImpl<User> testService;


    @RequestMapping("/")
    public String index() {
        testService.methodA(new User("张三"));
        return "/index.html";
    }

}
