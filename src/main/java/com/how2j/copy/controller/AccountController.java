package com.how2j.copy.controller;

import com.how2j.copy.pojo.User;
import com.how2j.copy.service.UserService;
import com.how2j.copy.util.AliyunSmsUtils;
import com.how2j.copy.util.CreateQRCode;
import com.how2j.copy.util.EmailSender;
import com.how2j.copy.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    UserService userService;


    CreateQRCode createQRCode = new CreateQRCode();
    @Autowired
    EmailSender emailSender;
    @Autowired
    RedisUtil redisUtil;



    @PostMapping("email_code")
    public String code1(String email, HttpSession session) {
        try {
            String code = "";
            for (int i = 0; i < 6; i++) {
                int temp = (int) (Math.random() * 10);
                code += temp;
            }
            emailSender.sendEmail(code, email);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @PostMapping("phone_code")
    public String code(String phone, HttpSession session) {

        String code = "";
        for (int i = 0; i < 6; i++) {
            int temp = (int) (Math.random() * 10);
            code += temp;
        }
        session.setAttribute("code", code);
        boolean flag = AliyunSmsUtils.send(code, phone);
        return flag ? "success" : "fail";
    }

    @PostMapping("/login/phone")
    public ModelAndView phLo(HttpSession session, String phone, String code) {
        String codeVail = (String) session.getAttribute("code");
        User user = userService.getByPhone(phone);
        System.out.println(phone + "==" + code );
        if (codeVail == null || !codeVail.equals(code) || user == null) {
            return new ModelAndView("redirect:/account/login/phone");
        }
        session.setAttribute("user", user);
        session.removeAttribute("code");
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }



    @GetMapping("/login")
    public ModelAndView login(String msg) {
        ModelAndView modelAndView = new ModelAndView("login");
modelAndView.addObject("msg",msg);
        return modelAndView;

    }

    @PostMapping("/login")
    public ModelAndView login_check(String username, String password, HttpSession session) {

        User user = userService.getByUsername(username);
        if (user == null || !(user.getPassword().equals(password))) {
            //error
            ModelAndView modelAndView = new ModelAndView("redirect:/account/login?msg=error");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/index");
            session.setAttribute("user", user);
            return modelAndView;
        }

    }

    @GetMapping("register")
    public ModelAndView register(String msg) {

        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("msg",msg);

        return modelAndView;
    }

    @PostMapping("register")
    public ModelAndView register_check(User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user1 = userService.getByUsername(user.getUsername());
        BCryptPasswordEncoder bCryptPasswordEncoder  = new BCryptPasswordEncoder();
        String  newP = bCryptPasswordEncoder.encode(user.getPassword());
        if (user1 == null && user.getPassword().length() >= 6 & user.getUsername().length()>=6) {
            user.setPassword(newP);
            userService.insert(user);
            modelAndView.setViewName("redirect:/index");
         //   session.setAttribute("user", user);
            return modelAndView;

        } else {

            modelAndView.setViewName("redirect:/account/register?msg=error");
            return modelAndView;
        }


    }

    @GetMapping("login/phone")
    public ModelAndView lp(String msg){
        ModelAndView modelAndView = new ModelAndView("loginByPhone");
        modelAndView.addObject("msg",msg);
        return modelAndView ;
    }
    @GetMapping("login/email")
    public ModelAndView le(String msg){
        ModelAndView modelAndView = new ModelAndView("loginByEmail");
        modelAndView.addObject("msg",msg);
        return modelAndView ;
    }


}
