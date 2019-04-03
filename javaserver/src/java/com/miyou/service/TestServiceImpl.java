package com.miyou.service;


import com.miyou.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestServiceImpl<T> implements  TestService<T>{

    public void methodA(T entity) {

        User user = (User) entity;
        log.info(user.getName());

    }
}
