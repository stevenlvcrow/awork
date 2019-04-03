package com.miyou.model;

import lombok.Data;

@Data
public class BusinessResponse {

    private String errCode = "0000";
    private String errInfo = "9999";

    private Object responseData;

    public BusinessResponse(){}

    public BusinessResponse(Object responseData){
        this.responseData=responseData;
    }


}
