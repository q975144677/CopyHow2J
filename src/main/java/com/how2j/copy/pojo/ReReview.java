package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`re_review`")
public class ReReview {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`rid`")
    private Integer rid;

    @Column(name = "`uid`")
    private Integer uid;

    @Column(name = "`content`")
    private String content;

    private User user ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
     * @return rid
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * @param rid
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
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