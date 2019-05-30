package com.how2j.copy.mapper;

import com.how2j.copy.pojo.UserAuthority;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserAuthorityMapper extends Mapper<UserAuthority>, MySqlMapper<UserAuthority> {
}