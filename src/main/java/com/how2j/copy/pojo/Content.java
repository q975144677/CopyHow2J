package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`content`")
public class Content {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`tid`")
    private Integer tid;

    @Column(name = "`content`")
    private String content;

    /**
     * @return Id
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
     * @return tid
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
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