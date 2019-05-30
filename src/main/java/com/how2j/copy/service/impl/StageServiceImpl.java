package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.StageMapper;
import com.how2j.copy.pojo.Stage;
import com.how2j.copy.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageServiceImpl implements StageService {

    @Autowired
    StageMapper stageMapper ;

    @Override
    public void delete(int id) {
    stageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Stage stage) {
    stageMapper.insert(stage);
    }

    @Override
    public void update(Stage stage) {
    stageMapper.updateByPrimaryKeySelective(stage);
    }

    @Override
    public List<Stage> list() {
        List<Stage> list = stageMapper.selectAll();

        return list;
    }

    @Override
    public Stage get(int id) {
       Stage stage =  stageMapper.selectByPrimaryKey(id);
        return stage;
    }
}
