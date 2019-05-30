package com.how2j.copy.mapper;

import com.how2j.copy.pojo.Review;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ReviewMapper extends Mapper<Review>, MySqlMapper<Review> {
}