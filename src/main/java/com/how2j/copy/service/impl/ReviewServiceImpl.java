package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.ReviewMapper;
import com.how2j.copy.pojo.ReReview;
import com.how2j.copy.pojo.Review;
import com.how2j.copy.pojo.User;
import com.how2j.copy.service.ReReviewService;
import com.how2j.copy.service.ReviewService;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    ReReviewService reReviewService ;

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public void insert(Review review) {
reviewMapper.insert(review);
    }

    @Override
    public List<Review> listByStid(int stid) {
        Example example = new Example(Review.class);
        example.createCriteria().andEqualTo("stid",stid);
        example.setOrderByClause("id desc");
        List<Review> list = reviewMapper.selectByExample(example );
        set(list);
        return list;
    }

    @Override
    public Review get(int id) {
        Review review = reviewMapper.selectByPrimaryKey(id);
        set(review);
        return review ;
    }
@Autowired
    UserService userService ;

    public void set(Review review ){
        List<ReReview> list = reReviewService.listByRid(review.getId());
        review.setReReviews(list);
        User user = userService.get(review.getUid());
        review.setUser(user);
    }

    public void set(List<Review> list){
        for(Review review : list){
            set(review);
        }

    }
}
