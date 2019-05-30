package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.BuyMapper;
import com.how2j.copy.pojo.Buy;
import com.how2j.copy.pojo.Item;
import com.how2j.copy.pojo.User;
import com.how2j.copy.service.BuyService;
import com.how2j.copy.service.ItemService;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BuyServiceImpl implements BuyService {
    @Autowired
    BuyMapper buyMapper;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Override
    public void delete(int id) {
        buyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Buy buy) {
        buyMapper.updateByPrimaryKeySelective(buy);
    }

    @Override
    public void insert(Buy buy) {
        buyMapper.insert(buy);
    }

    @Override
    public List<Buy> listByUid(int uid) {
        Example example = new Example(Buy.class);
        example.createCriteria().andEqualTo("uid", uid);
        example.setOrderByClause("id desc");
        List<Buy> list = buyMapper.selectByExample(example);
        set(list);
        return list;
    }

    @Override
    public List<Buy> listByIid(int iid) {
        Example example = new Example(Buy.class);
        example.createCriteria().andEqualTo("iid", iid);
        example.setOrderByClause("id desc");
        List<Buy> list = buyMapper.selectByExample(example);
        set(list);
        return list;
    }

    @Override
    public List<Buy> list() {
        List<Buy> list = buyMapper.selectAll();
        set(list);
        return list;
    }

    public void set(Buy buy) {
        Item item = itemService.get(buy.getIid());
        User user = userService.get(buy.getUid());
        buy.setItem(item);
        buy.setUser(user);

    }

    public void set(List<Buy> list) {
        for (Buy buy : list) {
            set(buy);
        }

    }

    @Override
    public Buy getByUidAndIid(int uid, int iid) {
        Example example = new Example(Buy.class);
        example.createCriteria().andEqualTo("uid",uid).andEqualTo("iid",iid);
        List<Buy> list = buyMapper.selectByExample(example);
        if(list.isEmpty()) {
            return null;
        }
        else{
            Buy buy = list.get(0);
            set(buy);
            return buy;
        }
    }
}
