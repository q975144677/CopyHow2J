package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`buy`")
public class Buy {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`uid`")
    private int uid;

    @Column(name = "`iid`")
    private int iid;

    private  Item item ;

    private User user ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {

        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
     * @return uid
     */
    public int getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * @return iid
     */
    public int getIid() {
        return iid;
    }

    /**
     * @param iid
     */
    public void setIid(int iid) {
        this.iid = iid;
    }
}