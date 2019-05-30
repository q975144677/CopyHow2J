package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`item`")
public class Item {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`sid`")
    private Integer sid;

    @Column(name = "`name`")
    private String name;
@Column(name = "`sub_title`")
    private String subTitle;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    private NeedToBuy needToBuy;

    public NeedToBuy getNeedToBuy() {
        return needToBuy;
    }

    public void setNeedToBuy(NeedToBuy needToBuy) {
        this.needToBuy = needToBuy;
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
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", sid=" + sid +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", needToBuy=" + needToBuy +
                '}';
    }
}