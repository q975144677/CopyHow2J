package com.how2j.copy.vo;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;

public class MimeSenderVo {
    private MimeMessage mimeMessage ;
    private JavaMailSenderImpl sender;

    public JavaMailSenderImpl getSender() {
        return sender;
    }

    public void setSender(JavaMailSenderImpl sender) {
        this.sender = sender;
    }

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }
}
