package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`user_authority`")
public class UserAuthority {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`uid`")
    private Integer uid;

    @Column(name = "`aid`")
    private Integer aid;

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
     * @return aid
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * @param aid
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }
}