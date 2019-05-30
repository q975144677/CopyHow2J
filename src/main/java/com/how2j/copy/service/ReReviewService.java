package com.how2j.copy.service;

import com.how2j.copy.pojo.ReReview;

import java.util.List;

public interface ReReviewService {
    void delete(int id);
    void update(ReReview reReview );
    void insert(ReReview reReview );
    List<ReReview> listByRid(int rid);
    ReReview get(int id);

    List<ReReview> listByUid(int uid);

}
