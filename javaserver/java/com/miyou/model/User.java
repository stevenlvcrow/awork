package com.miyou.model;

import lombok.Data;

@Data
public class User {

    String name;

    public User(String str){
        this.name = str;
    }
}
