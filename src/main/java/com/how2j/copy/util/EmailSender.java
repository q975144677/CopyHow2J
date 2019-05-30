package com.how2j.copy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.Properties;
@Component
public class EmailSender {
    /*
    邮箱 用户名
     */
    @Value("${mail.sender}")
    private String sender;
    /*
    邮件标题
     */
    @Value("${mail.subject}")
    private String subject;
    /*
    所用的thymeleaf模板
     */
    @Value("${mail.template}")
    private String template;
    /*
    授权码
     */
    @Value("${mail.password}")
    private String password;



    @Autowired
    SpringTemplateEngine springTemplateEngine ;

    public JavaMailSender createMailSender(String postPassword,String postBox){
        //System.out.println(postPassword+"="+postBox);
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl() ;
        javaMailSender .setPassword(postPassword);
        String s =postBox;
        javaMailSender .setUsername(s);
        javaMailSender.setDefaultEncoding("UTF-8");
        /*
        一般来说 163邮箱 的 smtp服务器就是 smtp.163.com
        qq 的就是 smtp.qq.com
        所以就通过你配置的邮箱来进行 动态的smtp的配置
        不过 如果邮箱的smtp服务器地址 不是这个规则的 ， 记得更改
         */
        String yu = s.split("@")[1].split("\\.")[0];
        javaMailSender .setHost("smtp."+yu+".com");
        Properties properties = new Properties() ;
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("spring.mail.properties.mail.smtp.starttls.required","true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender ;
    }
    public boolean sendEmail(String content, String receiver ){
        try{
           JavaMailSender mailSender =  createMailSender(password,sender);
        sendEmail(content,receiver,mailSender);}catch (Exception e ){
            return false;
        }
        return true;
    }
/*
这边template 需要的参数我直接 用字符串表示了。。
其实这样不大好 ， 用的话记得修改，最好是封装个templateVo 里面包含 要的参数之类的
 */
    public boolean sendEmail(String content, String receiver, JavaMailSender mailSender ){
        try {


                MimeMessage mm = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mm, true);
                helper.setFrom(sender);
                helper.setTo(receiver);
                helper.setSubject(subject);
                //html 转换为 thymeleaf
                Context context = new Context();
                context.setVariable("check", content);
                String text = springTemplateEngine.process(template, context);
                helper.setText(text, true);
                mailSender.send(mm);


        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
