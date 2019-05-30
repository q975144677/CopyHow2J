package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.AuthorityMapper;
import com.how2j.copy.pojo.Authority;
import com.how2j.copy.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    AuthorityMapper authorityMapper ;

    @Override
    public void delete(int id) {
        authorityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(Authority authority) {
        authorityMapper.insertSelective(authority);
    }

    @Override
    public void update(Authority authority) {
        authorityMapper.updateByPrimaryKeySelective(authority);
    }

    @Override
    public List<Authority> list() {
        List<Authority> list = authorityMapper.selectAll();
        return list;
    }

    @Override
    public Authority get(int id) {
        Authority authority = authorityMapper.selectByPrimaryKey(id);
       return authority ;
    }
}
