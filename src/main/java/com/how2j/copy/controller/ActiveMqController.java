package com.how2j.copy.controller;

import com.how2j.copy.pojo.User;
import com.how2j.copy.util.ActiveMqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActiveMqController {

@Autowired
    ActiveMqUtil activeMqUtil ;

    @JmsListener(destination = "consumer")
    public void ge(String j ){
        System.out.println(j);}

@GetMapping("send_ac")
    public String send(){
        User user = new User();
        user.setPhone("312421");
    return "/";
    }


}
