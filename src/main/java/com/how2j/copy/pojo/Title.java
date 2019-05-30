package com.how2j.copy.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "`title`")
public class Title {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`stid`")
    private String stid;

    @Column(name = "`name`")
    private String name;
//
//    private String username3 ;
//
//    public String getUsername3() {
//        return username3;
//    }
//
//    public void setUsername3(String username) {
//        this.username3 = username;
//    }

    private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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
     * @return stid
     */
    public String getStid() {
        return stid;
    }

    /**
     * @param stid
     */
    public void setStid(String stid) {
        this.stid = stid;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}