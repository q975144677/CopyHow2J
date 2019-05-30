package com.how2j.copy.service;

import com.how2j.copy.pojo.Item;

import java.util.List;

public interface ItemService {
    void delete(int id);
    void insert(Item item );
    void update(Item item );
    List<Item> listBySid(int sid);
    List<Item> list();
    Item get(int id);
}
