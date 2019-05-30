package com.how2j.copy.service;

import com.how2j.copy.pojo.Review;

import java.util.List;

public interface ReviewService {
    void delete(int id);
    void update(Review review );
    void insert(Review review );
    List<Review> listByStid(int stid);
    Review get(int id);
}
