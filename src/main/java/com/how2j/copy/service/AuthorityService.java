package com.how2j.copy.service;

import com.how2j.copy.pojo.Authority;

import java.util.List;

public interface AuthorityService {
    void delete(int id);
    void insert(Authority authority );
    void update(Authority authority );
    List<Authority> list();
    Authority get(int id);

}
