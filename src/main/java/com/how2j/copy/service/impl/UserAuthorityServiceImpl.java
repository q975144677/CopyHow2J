package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.UserAuthorityMapper;
import com.how2j.copy.pojo.UserAuthority;
import com.how2j.copy.service.UserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserAuthorityServiceImpl implements UserAuthorityService{

    @Autowired
    UserAuthorityMapper userAuthorityMapper ;

    @Override
    public void update(UserAuthority userAuthority) {
        userAuthorityMapper.updateByPrimaryKeySelective(userAuthority);
    }

    @Override
    public void delete(int id) {
        userAuthorityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(UserAuthority userAuthority) {
        userAuthorityMapper.insert(userAuthority);
    }

    @Override
    public List<UserAuthority> list() {
        List<UserAuthority> list = userAuthorityMapper.selectAll();
        return list;
    }

    @Override
    public List<UserAuthority> listByUid(int uid) {
        Example example =new Example(UserAuthority.class);
        example.createCriteria().andEqualTo("uid",uid);
        example.setOrderByClause("id desc");
        List<UserAuthority> list = userAuthorityMapper.selectByExample(example  );
        return list;
    }

    @Override
    public UserAuthority get(int id) {
        UserAuthority userAuthority = userAuthorityMapper.selectByPrimaryKey(id);
        return userAuthority;
    }
}
