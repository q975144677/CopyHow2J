package com.how2j.copy.service;

import com.how2j.copy.pojo.NeedToBuy;

import java.util.List;

public interface NeedToBuyService {

    void delete(int id);
    void insert(NeedToBuy needToBuy );
    NeedToBuy getByIid(int iid);
    NeedToBuy get(int id);
    List<NeedToBuy> list();

}
