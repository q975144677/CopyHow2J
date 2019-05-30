package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.ItemMapper;
import com.how2j.copy.pojo.Item;
import com.how2j.copy.pojo.NeedToBuy;
import com.how2j.copy.service.ItemService;
import com.how2j.copy.service.NeedToBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

@Autowired
    ItemMapper itemMapper ;

    @Override
    public void delete(int id) {
        itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(com.how2j.copy.pojo.Item item) {
    itemMapper.insert(item);
    }

    @Override
    public void update(com.how2j.copy.pojo.Item item) {
    itemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public List<com.how2j.copy.pojo.Item> listBySid(int sid) {
        Example example = new Example(Item.class);
        example.createCriteria().andEqualTo("sid",sid);
        example.setOrderByClause("id desc");
        List<Item> list = itemMapper.selectByExample(example );
        set(list);
        return list;
    }

    @Override
    public List<com.how2j.copy.pojo.Item> list() {
        List<Item> list = itemMapper.selectAll();
        set(list);
        return list;
    }

    @Override
    public com.how2j.copy.pojo.Item get(int id) {

        Item item = itemMapper.selectByPrimaryKey(id);
        set(item);
        return item;
    }

    @Autowired
    NeedToBuyService needToBuyService ;

    public void set(Item item ){
       NeedToBuy needToBuy = needToBuyService.getByIid(item.getId());
        item.setNeedToBuy(needToBuy);
    }

    public void set(List<Item> items){
        for(Item item : items){
            set(item);
        }
    }
}
