package com.how2j.copy.service;

import com.how2j.copy.pojo.Step;

import java.util.List;

public interface StepService {
    void delete(int id);
    void update(Step step );
    void insert(Step step);
    List<Step> listByIid(int iid);
    Step get(int id);

}
