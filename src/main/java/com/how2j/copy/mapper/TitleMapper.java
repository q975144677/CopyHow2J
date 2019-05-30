package com.how2j.copy.mapper;

import com.how2j.copy.pojo.Title;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TitleMapper extends Mapper<Title>, MySqlMapper<Title> {
}