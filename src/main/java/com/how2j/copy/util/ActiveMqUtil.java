package com.how2j.copy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqUtil {
@Autowired
JmsMessagingTemplate jmsMessagingTemplate ;

public void sendText(String text , String to ){
jmsMessagingTemplate.convertAndSend(to,text);
    System.out.println("***********发送成功**************");
    }


}
