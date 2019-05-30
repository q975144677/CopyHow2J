package com.how2j.copy.service;

import com.how2j.copy.pojo.Stage;

import java.util.List;

public interface StageService {

void delete(int id);
void insert(Stage stage);
void update(Stage stage );
List<Stage> list();
Stage get(int id);
}
