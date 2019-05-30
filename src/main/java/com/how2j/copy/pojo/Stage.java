package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`stage`")
public class Stage {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`name`")
    private String name;

    @Override
    public String toString() {
        return "Stage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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