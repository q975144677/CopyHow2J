package com.how2j.copy.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.how2j.copy.vo.Code;
import com.how2j.copy.vo.PhoneResponse;

public class AliyunSmsUtils {

    private static String mySign = "Ler4J";
    private static String myTemplate = "SMS_164513085";
    private static String accessKey = "LTAIH4zVEBDjvxXN";
    private static String accessSecret = "SkjcM446fhobxCUWNvoVkeuXquOryg";




    public static boolean send(String code,String target){
        Code code1 = new Code();
        code1.setCode(code);

        String body = JSONObject.toJSONString(code1);
//        System.out.println(body);
//        System.out.println("{\"code\":\"4124\"}");
        /***************************/


        //区域 阿里云测试上并不是必须要填写的
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //发送的目标
        request.putQueryParameter("PhoneNumbers", target);
        //签名名字，需要改成自己的
        request.putQueryParameter("SignName", mySign);
        //模板名字，需要改成自己的
        request.putQueryParameter("TemplateCode", myTemplate);
        //发送的验证码的code 需要遵守JSON格式

        request.putQueryParameter("TemplateParam", body);
        try {
            CommonResponse response = client.getCommonResponse(request);
          //  System.out.println(response.getData());
            PhoneResponse phoneResponse = JSONObject.parseObject(response.getData(),PhoneResponse.class);
            System.out.println(phoneResponse);
            if(!phoneResponse.getCode().equals("OK")){
                return false;
            }

        } catch (com.aliyuncs.exceptions.ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }

        return true;
        /****************************/

    }


    public static void main(String[] args) {

        System.out.println( send("133455",1331916619401L+""));

/*
        //区域 阿里云测试上并不是必须要填写的
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAIH4zVEBDjvxXN", "SkjcM446fhobxCUWNvoVkeuXquOryg");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        //发送的目标
        request.putQueryParameter("PhoneNumbers", "13916619401");
        //签名名字，需要改成自己的
        request.putQueryParameter("SignName", "Ler4J");
        //模板名字，需要改成自己的
        request.putQueryParameter("TemplateCode", "SMS_164513085");
        //发送的验证码的code 需要遵守JSON格式

        request.putQueryParameter("TemplateParam", "{\"code\":\"4124\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (com.aliyuncs.exceptions.ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }*/
    }
}
