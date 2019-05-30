package com.how2j.copy.service;

import com.how2j.copy.pojo.UserAuthority;

import java.util.List;

public interface UserAuthorityService {
    void update(UserAuthority userAuthority );
    void delete(int id);
    void insert(UserAuthority userAuthority );
    List<UserAuthority> list();
    List<UserAuthority> listByUid(int uid);
    UserAuthority get(int id);
}
