package com.how2j.copy.service;

import com.how2j.copy.pojo.Buy;

import java.util.List;

public interface BuyService {
 void delete(int id);
 void update(Buy buy);
 void insert(Buy buy );
 List<Buy> listByUid(int uid);
 List<Buy> listByIid(int iid);
 List<Buy> list();
 Buy getByUidAndIid(int uid, int iid);
}
