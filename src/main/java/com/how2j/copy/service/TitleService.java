package com.how2j.copy.service;

import com.how2j.copy.pojo.Title;

import java.util.List;

public interface TitleService {
    void delete(int id);
    void insert(Title title );
    void update(Title title );
    List<Title> listByStid(int stid);
    Title get(int id);
}
