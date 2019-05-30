package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.TitleMapper;
import com.how2j.copy.pojo.Content;
import com.how2j.copy.pojo.Title;
import com.how2j.copy.service.ContentService;
import com.how2j.copy.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {

    @Autowired
    TitleMapper titleMapper ;

    @Autowired
    ContentService contentService;

    @Override
    public void delete(int id) {
        titleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Title title) {
        titleMapper.insert(title);

    }

    @Override
    public void update(Title title) {
        titleMapper.updateByPrimaryKeySelective(title);
    }

    @Override
    public List<Title> listByStid(int stid) {
        Example example = new Example(Title.class);
        example.createCriteria().andEqualTo("stid",stid);
        example.setOrderByClause("id asc");
        List<Title> list = titleMapper.selectByExample(example );
        set(list);
        return list;
    }

    @Override
    public Title get(int id) {
        Title title = titleMapper.selectByPrimaryKey(id);

        return title;
    }

    public void set(Title title ){
        Content content = contentService.getByTid(title.getId());
        title.setContent(content);
    }

    public void set(List<Title> list){
        for(Title title : list){
            set(title);
        }

    }
}
