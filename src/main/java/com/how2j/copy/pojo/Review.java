package com.how2j.copy.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table(name = "`review`")
public class Review {
    @Id
    @Column(name = "`Id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`stid`")
    private Integer stid;

    @Column(name = "`uid`")
    private Integer uid;

    @Column(name = "`content`")
    private String content;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private List<ReReview> reReviews;

    public List<ReReview> getReReviews() {
        return reReviews;
    }

    public void setReReviews(List<ReReview> reReviews) {
        this.reReviews = reReviews;
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
     * @return tid
     */
    public Integer getStid() {
        return stid;
    }

    /**
     * @param tid
     */
    public void setStid(Integer stid) {
        this.stid = stid;
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