package com.how2j.copy.service;

import com.how2j.copy.pojo.Content;

public interface ContentService {
    void delete(int id);
    void update(Content content );
    void insert(Content content);
    Content getByTid(int tid);
    Content get(int id);

}
