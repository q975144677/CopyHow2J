package com.how2j.copy.controller;


import com.alibaba.fastjson.JSONObject;
import com.how2j.copy.pojo.Stage;
import com.how2j.copy.pojo.Step;
import com.how2j.copy.pojo.User;
import com.how2j.copy.service.StageService;
import com.how2j.copy.service.StepService;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/android")
public class AndroidController {

@Autowired
    UserService userService;
@Autowired
    StageService stageService ;
@Autowired
    StepService stepService ;
//10.0.2.2:8080/android/login


@PostMapping("/login")
    public User getUser(String username,String password){
        User user = userService.getByUsername(username);
        if(user==null || !user.getPassword().equals(password)){
                return null;
        }
    System.out.println(user);
        return user;
}




@RequestMapping("/test")
    public String test(User u, Integer id, HttpServletRequest request ){


    /*******************************/
    System.out.println(request.getParameter("xml"));
        System.out.println(request.getAttribute("xml"));
        System.out.println(id);
        System.out.println(u.toString());
ModelAndView m = new ModelAndView("rr");
    Map<String,Object> map = new HashMap<>();
    List<User> list = userService.list();
    List<Stage> list1 = stageService.list();
    List<Step> list2 = stepService.listByIid(4);
    map.put("user",list);
    map.put("stage",list1);
    map.put("step",list2);
    map.put("test","test");

    JSONObject jsonObject = new JSONObject(map);
    map = JSONObject.parseObject(JSONObject.toJSONString(jsonObject),Map.class);


    String x = jsonObject.toJSONString();
   // System.out.println(x);
    JSONObject jj = JSONObject.parseObject(x);
  //  System.out.println(jj.toJSONString());
    Set<String> set = map.keySet();
    for(String key : set){
      //  System.out.println(map.get(key));
    }
    User user = list.get(0);
    map = new HashMap<>();

    JSONObject jsonObject1 = JSONObject.parseObject(JSONObject.toJSONString(user));
return jsonObject1.toJSONString();
}


}
