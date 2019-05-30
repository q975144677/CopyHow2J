package com.how2j.copy.mapper;

import com.how2j.copy.pojo.Item;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ItemMapper extends Mapper<Item>, MySqlMapper<Item> {
}