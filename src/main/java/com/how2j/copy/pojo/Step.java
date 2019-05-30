package com.how2j.copy.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "`step`")
public class Step {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`iid`")
    private Integer iid;

    @Column(name = "`name`")
    private String name;

    private List<Title> titles;

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
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
     * @return iid
     */
    public Integer getIid() {
        return iid;
    }

    /**
     * @param iid
     */
    public void setIid(Integer iid) {
        this.iid = iid;
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