package com.miyou.quartz.service;

import com.miyou.model.User;
import com.miyou.service.TestService;
import com.miyou.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class QuartzThread implements Runnable{
    @Override
    public void run() {
        try {
            //获取bean
            TestService testService = SpringUtil.getBean(TestService.class);
            //执行任务
            testService.methodA(new User("张三"));
            log.info("当前时间:"+LocalDateTime.now());
            log.info("执行成功");
        } catch (Exception e) {
            log.error("执行失败: " + e.getLocalizedMessage());
        }
    }
}
