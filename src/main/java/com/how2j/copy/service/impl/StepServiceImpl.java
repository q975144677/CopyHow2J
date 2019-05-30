package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.StepMapper;
import com.how2j.copy.pojo.Step;
import com.how2j.copy.pojo.Title;
import com.how2j.copy.service.StepService;
import com.how2j.copy.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class StepServiceImpl   implements StepService{

    @Autowired
    StepMapper stepMapper ;

    @Autowired
    TitleService titleService ;

    @Override
    public void delete(int id) {
        stepMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Step step) {
        stepMapper.updateByPrimaryKeySelective(step);

    }

    @Override
    public void insert(Step step) {
        stepMapper.insert(step);
    }

    @Override
    public List<Step> listByIid(int iid) {
        Example example = new Example(Step.class);
        example.createCriteria().andEqualTo("iid",iid);
        example.setOrderByClause("id asc");
        List<Step> list = stepMapper.selectByExample(example );
        set(list);
        return list;
    }

    @Override
    public Step get(int id) {
        Step step = stepMapper.selectByPrimaryKey(id);
        return step;
    }

    public void set(Step step ){
      List<Title> list =  titleService.listByStid(step.getId());
        step.setTitles(list);
    }

    public void set(List<Step> steps){
        for(Step step : steps){
            set(step);
        }

    }



}
