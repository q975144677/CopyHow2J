package com.how2j.copy.consumer;

import com.alibaba.fastjson.JSONObject;
import com.how2j.copy.util.ActiveMqUtil;
import com.how2j.copy.util.AliyunSmsUtils;
import com.how2j.copy.util.EmailSender;
import com.how2j.copy.vo.EmailVo;
import com.how2j.copy.vo.MimeSenderVo;
import com.how2j.copy.vo.SmsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class ActiveMqSenderConsumer {

@Autowired
    ActiveMqUtil activeMqUtil ;
@Autowired
    EmailSender emailSender ;
@JmsListener(destination = "emailSend")
public void s(String json){
    EmailVo vo  = JSONObject.parseObject(json,EmailVo.class);
    System.out.println(vo.getContent());
    emailSender.sendEmail(vo.getContent(),vo.getTo());

}

@JmsListener(destination = "emailSender")
    public void send_email(String json){
    MimeSenderVo mimeSenderVo = JSONObject.parseObject(json,MimeSenderVo.class);
    MimeMessage mimeMessage  = mimeSenderVo.getMimeMessage();
    JavaMailSenderImpl sender = mimeSenderVo.getSender();
    sender.send(mimeMessage);
}

@JmsListener(destination = "smsSender")
    public void send_sms(String json){
    SmsVo smsVo = JSONObject.parseObject(json,SmsVo.class);
    boolean flag = AliyunSmsUtils.send(smsVo.getText(), smsVo.getTo());
}

}
