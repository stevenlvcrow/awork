package com.miyou.service;

import com.alibaba.fastjson.JSONObject;
import com.miyou.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class HttpServiceImpl {

    //偿债能力接口

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private String URL;

    private void changzhai(JSONObject queryObj){
        URL = "https://test-risk.xhy.com/api/solvency";

        queryObj.put("partnerCode", "2018120509219538");//
        queryObj.put("partnerKey", "8467480fa2441579efd7e9c7e24fcc99");//key
        queryObj.put("outTradeNo", "1073101");//64;--partnerCode+
        queryObj.put("requestTime", sdf.format(new Date()));//yyyyMMddHHmmssSSS
        JSONObject body = new JSONObject();
        body.put("identity", "420625199207261531");
        body.put("name", "李智杰");
        body.put("mobile", "17607105677");
        queryObj.put("body", body);
    }



    public void tielu(JSONObject queryObj){
        URL = "http://39.107.71.193/admin/companyconfig/fileapi/gatfile?fileName=repay.csv&type=1&abbreviate=jbdwl";

//        queryObj.put("fileName", "repay.csv");//
//        queryObj.put("type", "1");//key
//        queryObj.put("abbreviate", "jbdwl");
        JSONObject body = new JSONObject();
//        body.put("identity", "420625199207261531");
//        queryObj.put("body", body);
    }

    public void pingji(JSONObject queryObj){
        URL = "https://test-risk.xhy.com/api/mobileBehavior/query";

        queryObj.put("partnerCode", "2018120509219538");//
        queryObj.put("partnerKey", "8467480fa2441579efd7e9c7e24fcc99");//key
        queryObj.put("outTradeNo", "1073101");//64;--partnerCode+
        queryObj.put("requestTime", sdf.format(new Date()));//yyyyMMddHHmmssSSS
        JSONObject body = new JSONObject();
        body.put("mobile", "17607105677");
        queryObj.put("body", body);
    }


    private void remoat(JSONObject queryObj){
        String resultStr = HttpClientUtil.doPostJson(URL, queryObj.toJSONString());
        if (StringUtils.isNotEmpty(resultStr)) {
            try {
                JSONObject resultObj = JSONObject.parseObject(resultStr);
                System.out.println(resultObj);
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {

        String URL = "http://39.107.71.193/admin/companyconfig/fileapi/gatfile?fileName=repay.csv&type=1&abbreviate=jbdwl";
        String str =  HttpClientUtil.doUpload(URL,new File("D:/repay.csv"));
        System.out.println(str);
    }


}
