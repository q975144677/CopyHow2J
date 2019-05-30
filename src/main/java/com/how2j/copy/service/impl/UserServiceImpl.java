package com.how2j.copy.service.impl;

import com.how2j.copy.mapper.UserMapper;
import com.how2j.copy.pojo.Authority;
import com.how2j.copy.pojo.User;
import com.how2j.copy.pojo.UserAuthority;
import com.how2j.copy.service.AuthorityService;
import com.how2j.copy.service.UserAuthorityService;
import com.how2j.copy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {


    @Value("${default.authority}")
int da;
    @Autowired
    UserMapper userMapper ;
@Autowired
    UserAuthorityService userAuthorityService;
@Autowired
    AuthorityService authorityService;
    @Override
    public void delete(int id) {

        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void insert(User user) {

        userMapper.insert(user);
        int uid = user.getId();
                UserAuthority userAuthority  = new UserAuthority();
        userAuthority.setAid(da);
        userAuthority.setUid(uid);
        userAuthorityService
                .insert(userAuthority);


    }

    @Override
    public List<User> list() {

       List<User> list = userMapper.selectAll();
       set(list);
        return list;
    }

    @Override
    public User getByUsername(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
        List<User> list = userMapper.selectByExample(example );
        if(list.isEmpty())
        return null;

        else
            set(list);
            return list.get(0);
    }

    @Override
    public User getByPhone(String phone) {

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("phone",phone);
        List<User> list = userMapper.selectByExample(example );
        if(list.isEmpty())
            return null;

        else
            set(list);
            return list.get(0);

    }

    @Override
    public User getByEmail(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email",email );
        List<User> list = userMapper.selectByExample(example );
        if(list.isEmpty())
            return null;

        else
            set(list);
            return list.get(0);
    }

    @Override
    public User get(int id) {


              User user =  userMapper.selectByPrimaryKey(id);
              set(user);
              return user ;
    }

    public void set(User user){
        if(user == null || user.getId() ==  null || user.getId() == 0){
return;
        }
List<UserAuthority> list = userAuthorityService.listByUid(user.getId());
    List<Authority> result = new ArrayList<>();
    for(UserAuthority userAuthority : list){
        Authority authority = authorityService.get(userAuthority.getAid());
        result.add(authority );
    }
    user.setAuthorities(result);
    }
    public void set(List<User> users){
        for(User user1 : users){
            set(user1);
        }
    }
    /*
    稍作修改 邮箱手机 账户都可以通过密码登录
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       User user =  getByUsername(s);
       if(user == null){
           user = getByEmail(s);
       }
       if(user == null){
           user = getByPhone(s);
       }
       set(user);
        return user;
    }

    @Override
    public User getInstance() {
        Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
//details里面可能存放了当前登录用户的详细信息，也可以通过cast后拿到
        if(authenticationToken instanceof  AnonymousAuthenticationToken){
            System.out.println("匿名");
            return null ;
        }
        else{
            authenticationToken = (UsernamePasswordAuthenticationToken)authenticationToken;
        }
        System.out.println(authenticationToken);
        User user = (User)authenticationToken.getPrincipal();

        return user;
    }
}
