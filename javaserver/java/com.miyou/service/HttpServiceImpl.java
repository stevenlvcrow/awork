package com.miyou.service;

import com.alibaba.fastjson.JSONObject;
import com.miyou.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

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
        body.put("identity", "420625199207201531");
        body.put("name", "lizhijie");
        body.put("mobile", "17607105677");
        queryObj.put("body", body);
    }



    public void tielu(JSONObject queryObj){
        URL = "https://test-risk.xhy.com/api/railwayTimes";

        queryObj.put("partnerCode", "2018120509219538");//
        queryObj.put("partnerKey", "8467480fa2441579efd7e9c7e24fcc99");//key
        queryObj.put("outTradeNo", "1073101");//64;--partnerCode+
        queryObj.put("requestTime", sdf.format(new Date()));//yyyyMMddHHmmssSSS
        JSONObject body = new JSONObject();
        body.put("", "");
        queryObj.put("body", body);
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
                boolean success = resultObj.getBooleanValue("success");
                if (success) {
                    String data = resultObj.getString("data");
                    if (StringUtils.isNotEmpty(data)) {
                        Map<String, Object> resultMap = JSONObject.parseObject(data, Map.class);
                        System.out.println(resultMap);
                        System.out.println(asciiToString(""+resultMap.get("msg")));
//
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        HttpServiceImpl httpService = new HttpServiceImpl();
        JSONObject queryObj = new JSONObject();
        //httpService.tielu(queryObj);
        httpService.changzhai(queryObj);
        httpService.remoat(queryObj);
    }

    public static String asciiToString(String value)
    {
        StringBuilder sbu = new StringBuilder();
        String[] chars = value.split(",");
        for (String aChar : chars) {
            sbu.append((char) Integer.parseInt(aChar));
        }
        return sbu.toString();
    }
}
