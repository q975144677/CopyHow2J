package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.ReReviewMapper;
import com.how2j.copy.pojo.ReReview;
import com.how2j.copy.pojo.User;
import com.how2j.copy.service.ReReviewService;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ReReviewServiceImpl implements ReReviewService {

    @Autowired
    ReReviewMapper reReviewMapper ;

    @Override
    public void delete(int id) {
        reReviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ReReview reReview) {
        reReviewMapper.updateByPrimaryKeySelective(reReview);
    }

    @Override
    public void insert(ReReview reReview) {
        reReviewMapper.insert(reReview);
    }

    @Override
    public List<ReReview> listByRid(int rid) {
        Example example = new Example(ReReview.class);
        example.setOrderByClause("id desc");
        example.createCriteria().andEqualTo("rid",rid);
        List<ReReview> list = reReviewMapper.selectByExample(example );
set(list);
        return list;
    }

    @Override
    public ReReview get(int id) {
      ReReview reReview = reReviewMapper.selectByPrimaryKey(id);
set(reReview );
        return reReview;
    }
@Autowired
    UserService userService;

public void set(ReReview reReview ){
    User user = userService.get(reReview.getUid());
    reReview.setUser(user);

}

    @Override
    public List<ReReview> listByUid(int uid) {
        Example example = new Example(ReReview.class);
        example.setOrderByClause("id desc");
        example.createCriteria().andEqualTo("uid",uid);
        List<ReReview> list = reReviewMapper.selectByExample(example );
        set(list);
        return list;


    }

    public void set(List<ReReview> reReviews ){
    for(ReReview reReview : reReviews ){
        set(reReview);
    }

}
}
