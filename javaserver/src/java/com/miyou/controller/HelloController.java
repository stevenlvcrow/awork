package com.miyou.controller;

import com.miyou.model.User;
import com.miyou.service.TestServiceImpl;
import com.miyou.utils.PythonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

@Controller
public class HelloController {

    @Autowired
    TestServiceImpl<User> testService;

    @Autowired
    PythonUtils pythonUtils;

    @RequestMapping("/")
    public String index() {
        testService.methodA(new User("张三"));
        try {
            pythonUtils.invokPython("xiaohuar.py");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("测试修改");
        return "/index.html";
    }

}
