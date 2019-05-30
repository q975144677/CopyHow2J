package com.how2j.copy.pojo;

import javax.persistence.*;

@Table(name = "`need_to_buy`")
public class NeedToBuy {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`iid`")
    private Integer iid;

    @Column(name = "`price`")
    private String price;

    @Override
    public String toString() {
        return "NeedToBuy{" +
                "id=" + id +
                ", iid=" + iid +
                ", price='" + price + '\'' +
                ", item=" + item +
                '}';
    }

    private Item item ;

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
     * @return price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
    }
}