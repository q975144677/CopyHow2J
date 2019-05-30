package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.ContentMapper;
import com.how2j.copy.pojo.Content;
import com.how2j.copy.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

@Autowired
    ContentMapper contentMapper ;

    @Override
    public void delete(int id) {
        contentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Content content) {
        contentMapper.updateByPrimaryKeySelective(content);
    }

    @Override
    public void insert(Content content) {
        contentMapper.insert(content);
    }

    @Override
    public Content getByTid(int tid) {

        Example example = new Example(Content.class);
        example.createCriteria().andEqualTo("tid",tid);

        List<Content> list = contentMapper.selectByExample(example );
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }


    }

    @Override
    public Content get(int id) {
        Content content = contentMapper.selectByPrimaryKey(id);

        return content;
    }
}
