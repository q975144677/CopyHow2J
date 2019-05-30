package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.ItemMapper;
import com.how2j.copy.mapper.NeedToBuyMapper;
import com.how2j.copy.pojo.Item;
import com.how2j.copy.pojo.NeedToBuy;
import com.how2j.copy.service.ItemService;
import com.how2j.copy.service.NeedToBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class NeedToBuyServiceImpl implements NeedToBuyService {

   @Autowired
    NeedToBuyMapper needToBuyMapper;
   @Autowired
    ItemService itemService ;
    @Override
    public void delete(int id) {
        needToBuyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(NeedToBuy needToBuy) {
        needToBuyMapper.insert(needToBuy);
    }

    @Override
    public NeedToBuy getByIid(int iid) {

        Example example = new Example(NeedToBuy.class);
        example.createCriteria().andEqualTo("iid",iid);
        example.setOrderByClause("id desc");
        List< NeedToBuy> list = needToBuyMapper.selectByExample(example );
        if(list.isEmpty()) {
            return null;
        }
        set(list);
        return list.get(0);
    }

    @Override
    public List<NeedToBuy> list() {
        List<NeedToBuy> list = needToBuyMapper.selectAll();
set(list);
        return list;
    }

    @Override
    public NeedToBuy get(int id) {
        NeedToBuy needToBuy = needToBuyMapper.selectByPrimaryKey(id);
        if(needToBuy != null)
        set(needToBuy);
        return needToBuy ;
    }


    @Autowired
    ItemMapper itemMapper ;
    public void set(NeedToBuy needToBuy ){
        Item item = itemMapper.selectByPrimaryKey(needToBuy.getIid());
        needToBuy.setItem(item );

    }

    public void set(List<NeedToBuy> list){
        for(NeedToBuy needToBuy : list){
            set(needToBuy);
        }

    }
}
