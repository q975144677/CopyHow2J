package com.how2j.copy.service;

import com.how2j.copy.pojo.User;

import java.util.List;

public interface UserService {
    void delete(int id);
    void update(User user );
    void insert(User user );
    List<User> list();
    User getByUsername(String username);
    User get(int id);
    User getByPhone(String phone);
    User getByEmail(String email);
    User getInstance();
}
