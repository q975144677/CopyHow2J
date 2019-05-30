package com.how2j.copy.vo;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "`email`")
public class Email implements Serializable {private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;


    @Column(name = "`receiveEmail`")
    private String receiveemail;

    @Column(name = "`subject`")
    private String subject;

    @Column(name = "`template`")
    private String template;

    @Column(name = "`send_time`")
    private Date sendTime;

    @Column(name = "`content`")
    private String content;

    private MultipartFile file;



    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return receiveEmail
     */
    public String getReceiveemail() {
        return receiveemail;
    }

    /**
     * @param receiveemail
     */
    public void setReceiveemail(String receiveemail) {
        this.receiveemail = receiveemail;
    }

    /**
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return send_time
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }


}