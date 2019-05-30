package com.how2j.copy.controller;

import com.alibaba.fastjson.JSONObject;
import com.how2j.copy.pojo.*;
import com.how2j.copy.service.*;
import com.how2j.copy.util.ActiveMqUtil;
import com.how2j.copy.util.PostBoxVail;
import com.how2j.copy.util.RedisUtil;
import com.how2j.copy.vo.Code;
import com.how2j.copy.vo.EmailVo;
import com.how2j.copy.vo.SmsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ForeController {
    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Autowired
    NeedToBuyService needToBuyService;

    @Autowired
    ActiveMqUtil activeMqUtil;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;
    @Autowired
    StageService stageService;

    @Autowired
    ItemService itemService;
    @Autowired
    StepService stepService;
    @Autowired
    TitleService titleService;
    @Autowired
    ContentService contentService;
    @Autowired
    ReReviewService reReviewService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    BuyService buyService;
    @Value("${mail.sender}")
    String postUsername;
    @Value("${password}")
    String postPassword;
    @Value("${host}")
    String postHost;

    @GetMapping("/index")
    public ModelAndView modelAndView() {
        return new ModelAndView("redirect:/");

    }

    @GetMapping("/")
    public ModelAndView index() {
        try {

            ModelAndView modelAndView = new ModelAndView("redirect:/stage/" + stageService.list().get(0).getId());
            return modelAndView;

        } catch (Exception e) {
            return new ModelAndView("403");

        }
    }

    @GetMapping("/stage/{id}")
    public ModelAndView stage(@PathVariable("id") int id) {
        List<Item> items = itemService.listBySid(id);
        List<Stage> stages = stageService.list();
        ModelAndView modelAndView = new ModelAndView("stage");
        modelAndView.addObject("items", items);
        modelAndView.addObject("stages", stages);
        modelAndView.addObject("now", id);

        return modelAndView;
    }

    @GetMapping("item/{id}")
    public ModelAndView item(@PathVariable("id") int id, @RequestParam(value = "step", defaultValue = "1") int step, HttpSession session, @RequestParam(value = "titleId", defaultValue = "0") Integer tid,@RequestParam(defaultValue = "0",value = "tindex")String tindex) {
        //check isNeedToBuy

        NeedToBuy needToBuyCheck = needToBuyService.getByIid(id);
        if (needToBuyCheck != null) {
            //    User user = (User) session.getAttribute("user");
            User user = userService.getInstance();
            if (user == null) {
                return new ModelAndView("redirect:/account/login");
            }
            Buy buy = buyService.getByUidAndIid(user.getId(), id);
            if (buy == null) {
                return new ModelAndView("redirect:/item/" + id + "/buy");
            }
        }

        List<Step> steps = stepService.listByIid(id);
        step = step - 1;
        step = steps.size() <= step ? steps.size() - 1 : (step > 0 ? step : 0);
        if (steps.isEmpty()) {
            return new ModelAndView("need_to_add");
        }
        Step now = steps.get(step);
        List<Review> reviews = reviewService.listByStid(now.getId());
        ModelAndView modelAndView = new ModelAndView("detail");
        boolean pre = step == 1 ? false : true;
        boolean next = step == steps.size() ? false : true;
        Item item = itemService.get(id);
        modelAndView.addObject("page", step + 1);
        modelAndView.addObject("pre", pre);
        modelAndView.addObject("next", next);
        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("steps", steps);
        modelAndView.addObject("step", now);
        modelAndView.addObject("item", item);
        modelAndView.addObject("tid", tid);
        modelAndView.addObject("tindex", tindex);
        if (tid == 0) {
            List<Title> titles = now.getTitles();
            if (!titles.isEmpty()) {
                tid = titles.get(0).getId();
            }
        }
        modelAndView.addObject("tid", tid);
        NeedToBuy needToBuy = needToBuyService.getByIid(id);
        boolean flag = needToBuy == null ? false : true;
        modelAndView.addObject("need2Buy", flag);
        return modelAndView;
    }

    @PostMapping("review")
    public ModelAndView review(String content, HttpSession session, int stid) {
        // User user = (User) session.getAttribute("user");
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        Review review = new Review();
        review.setUid(user.getId());
        review.setStid(stid);
        review.setContent(content);
        reviewService.insert(review);
        Step step = stepService.get(stid);
        int iid = step.getIid();
        List<Step> list = stepService.listByIid(iid);
        int result = 0;
        for (Step step1 : list) {
            result++;
            if (step1.getId() == stid) {
                break;
            }
        }
        return new ModelAndView("redirect:/item/" + iid + "?step=" + result);


    }

    @GetMapping("item/{id}/buy")
    public ModelAndView buy(@PathVariable("id") int id, HttpSession session) {
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        ModelAndView modelAndView = new ModelAndView("buy");
        Item item = itemService.get(id);
        NeedToBuy needToBuy = needToBuyService.getByIid(item.getId());
        if (needToBuy == null) {
            return new ModelAndView("no_need_to_buy");
        }
        modelAndView.addObject("item", item);
        modelAndView.addObject("price", needToBuy.getPrice());

        return modelAndView;
    }

    @PostMapping("re_review")
    public ModelAndView rereview(ReReview reReview, HttpSession session) {
        // redisUtil.get("")
        ModelAndView modelAndView = new ModelAndView();
        // User user = (User) session.getAttribute("user");
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        Object o = redisUtil.get(user.getId() + "");
        List<ReReview> list = (List<ReReview>) o;
        if (list == null) {
            list = new ArrayList<>();
        }
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        reReview.setUid(user.getId());
        reReviewService.insert(reReview);
        reReview = reReviewService.get(reReview.getId());
        list.add(reReview);
        redisUtil.set("" + user.getId(), list);
        Review review = reviewService.get(reReview.getRid());

        Step step = stepService.get(review.getStid());

        Item item = itemService.get(step.getIid());

        List<Step> steps = stepService.listByIid(item.getId());
        int result = 0;
        for (Step step1 : steps) {
            result++;
            if (step1.getId() == step.getId()) {
                break;
            }
        }
        if (user == null) {
            modelAndView.setViewName("redirect:/account/login");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/item/" + item.getId() + "?step=" + result);
        return modelAndView;

    }

    @PostMapping("buy/{id}")
    public ModelAndView buyOne(@PathVariable("id") int id, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        // User user = (User) session.getAttribute("user");
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        Item item = itemService.get(id);
        Buy buy = new Buy();
        buy.setUid(user.getId());
        buy.setIid(id);
        if (buyService.getByUidAndIid(user.getId(), id) != null) {
            //已经买过了
            return new ModelAndView("/");
        }
        buyService.insert(buy);
        return new ModelAndView("redirect:/item/" + id);

    }

    @PostMapping("/send_email")
    public String sendE(String to, HttpSession session) {
        Long l = System.currentTimeMillis();
        //vail the email
        JSONObject jsonObject = PostBoxVail.valid(to);
        Code code = JSONObject.parseObject(jsonObject.toJSONString(), Code.class);

        System.out.println(code.getCode().toString());

        if (!code.getCode().equals(200 + "")) {
            return "not_exist";
        }
    /*
    生成 验证码
    */
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ; i<6 ; i++){
            stringBuilder.append((int)(10*Math.random()));
        }
        session.setAttribute("e_code"+to,stringBuilder.toString());
        /*
        activeMq 发送
         */
        EmailVo emailVo = new EmailVo();
        emailVo.setContent(stringBuilder.toString());
        emailVo.setTo(to);
        String body = JSONObject.toJSONString(emailVo);
        System.out.println(System.currentTimeMillis() - l +"--------用时");
        activeMqUtil.sendText(body,"emailSend");



//        JavaMailSenderImpl sender = new JavaMailSenderImpl();
//        sender.setDefaultEncoding("UTF-8");
//        System.out.println(postUsername+"--"+postPassword+"---"+postHost);
//        sender.setUsername(postUsername);
//        sender.setPassword(postPassword);
//        sender.setHost(postHost);
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.auth", "true");
//        properties.setProperty("mail.smtp.starttls.enable", "true");
//        properties.setProperty("spring.mail.properties.mail.smtp.starttls.required", "true");
//        sender.setJavaMailProperties(properties);
//
//        try {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < 6; i++) {
//                sb.append((int) (Math.random() * 10));
//            }
//            MimeMessage mm = sender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mm, true);
//            helper.setFrom(postUsername);
//            helper.setTo(to);
//            helper.setSubject("How2J仿站验证码");
//            //html 转换为 thymeleaf
//            Context context = new Context();
//            context.setVariable("check", sb.toString());
//            String text = springTemplateEngine.process("check", context);
//            helper.setText(text, true);
//            /*
//            改为 activeMQ 队列模式
//             */
//            MimeSenderVo vo = new MimeSenderVo();
//            vo.setMimeMessage(mm);
//            vo.setSender(sender);
//            String body = JSONObject.toJSONString(vo);
//            activeMqUtil.sendText(body,"emailSender");
//            //sender.send(mm);
//            System.out.println(sb.toString());
//            session.setAttribute("e_code"+to,sb.toString());
//            System.out.println("e_code"+to);
//            System.out.println(session.getAttribute("e_code"+to));
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

        return "success";
    }

    @PostMapping("e_code_vail")
    public ModelAndView eV(@RequestParam(value = "email", defaultValue = "none") String postbox, String code, HttpSession session) {
        boolean flag = false;
        System.out.println("e_code" + postbox);
        String e_code = (String) session.getAttribute("e_code" + postbox);
        if (e_code == null || e_code.isEmpty() || !e_code.equals(code)) {
            System.out.println(e_code + "**" + code);
            return new ModelAndView("redirect:/my?msg=e_error");
        }
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        user.setEmail(postbox);
        userService.update(user);
        return new ModelAndView("redirect:/my?msg=success");
    }

    @PostMapping("p_code_vail")
    public ModelAndView pc(String phone, String code, HttpSession session) {
        String ncode = (String) session.getAttribute("p_code" + phone);
        if (ncode == null || !ncode.equals(code)) {
            return new ModelAndView("redirect:/my?msg=p_error");
        }
        User user = userService.getInstance();
        user.setPhone(phone);
        userService.update(user);
        return new ModelAndView("redirect:/my?msg=success");

    }

    @PostMapping("send_phone")
    public String sp(String phone, HttpSession session) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append((int) (10 * Math.random()));
        }
        String code = stringBuilder.toString();
        try {
            /*
            改为 activeMQ 队列模式
             */
            // boolean flag = AliyunSmsUtils.send(code, phone);
            boolean flag = true;
            SmsVo smsVo = new SmsVo();
            smsVo.setText(code);
            smsVo.setTo(phone);
            String body = JSONObject.toJSONString(smsVo);
            activeMqUtil.sendText(body, "smsSender");

            //System.out.println(flag);
            if (!flag) {
                return "fail";
            }
            session.setAttribute("p_code" + phone, code);
        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("pays")
    public ModelAndView paies() {
        ModelAndView modelAndView = new ModelAndView("pays");

        List<NeedToBuy> list = needToBuyService.list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getItem() == null) {
                list.remove(i);
                i--;
            }
        }
        modelAndView.addObject("pays", list);
        System.out.println(list);
        return modelAndView;

    }

    @PostMapping("change/{username}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView change(User user, @PathVariable("username") String username, String old) {
        ModelAndView modelAndView = new ModelAndView("redirect:/my?msg=success");
        System.out.println(user.getPassword());
        User oUser = userService.get(user.getId());
        if (!new BCryptPasswordEncoder().matches(old, oUser.getPassword())) {
            return new ModelAndView("redirect:/my?msg=fail");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.update(user);
        return modelAndView;
    }

    @GetMapping("my")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ModelAndView my(HttpSession session, String msg) {
        // User user = (User)session.getAttribute("user");
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }

        ModelAndView modelAndView = new ModelAndView("my");
        modelAndView.addObject("msg", msg);
        modelAndView.addObject("user", user);
        return modelAndView;

    }

    @GetMapping("msg")
    public ModelAndView msg(HttpSession session) {
        // User user = (User) session.getAttribute("user");
        User user = userService.getInstance();
        if (user == null) {
            return new ModelAndView("redirect:/account/login");
        }
        int id = user.getId();
        List<ReReview> list = reReviewService.listByUid(id);
        ModelAndView modelAndView = new ModelAndView("/msg");
        List<ReReview> list2 = null;
        try {
            list2 = (List<ReReview>) redisUtil.get("" + user.getId());
            if (list2 == null || list2.isEmpty()) {
                list2 = new ArrayList<>();
            }
            Map<ReReview, Boolean> map = new HashMap<>();
            for (ReReview rr : list) {
                if (list2.contains(rr)) {
                    map.put(rr, true);
                } else {
                    map.put(rr, false);
                }
            }
        } catch (Exception e) {
            list2 = new ArrayList<>();
        }
        // modelAndView.addObject("map",map);
        modelAndView.addObject("noReads", list2);
        List<ReReview> read = new ArrayList<>(list);
        for (int i = 0; i < read.size(); i++) {
            if (list2.contains(read.get(i))) {
                read.remove(i--);
            }
        }
        modelAndView.addObject("read", read);
        modelAndView.addObject("msgs", list);
        // System.out.println(list);
        //System.out.println(read);
        //System.out.println(list2);
        redisUtil.del("" + user.getId());

        return modelAndView;

    }


    @PostMapping("get_no_read")
    public String getNoRead(HttpSession session) throws Exception {
//        User user = (User) session.getAttribute("user");
        User user = userService.getInstance();
        if (user == null) {
            return "false";
        }

        List<ReReview> list = (List<ReReview>) redisUtil.get("" + user.getId());
        if (list == null || list.isEmpty()) {
            return false + "";
        }

        return "" + list.size();

    }
    @GetMapping("complain/{username}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView com(@PathVariable("username")String username){
        ModelAndView modelAndView = new ModelAndView("complain");
        User user = userService.getInstance();
        modelAndView.addObject("user",user);
        return modelAndView ;
    }

}
