package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`content_picture`")
public class ContentPicture {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`cid`")
    private String cid;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`width`")
    private Integer width;

    @Column(name = "`height`")
    private Integer height;

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
     * @return cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(String cid) {
        this.cid = cid;
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

    /**
     * @return width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }
}