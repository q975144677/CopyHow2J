package com.how2j.copy.mapper;

import com.how2j.copy.pojo.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
}