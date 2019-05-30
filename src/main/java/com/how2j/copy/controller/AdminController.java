package com.how2j.copy.controller;

import com.how2j.copy.pojo.*;
import com.how2j.copy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    ContentService contentService;
    @Autowired
    ItemService itemService;
    @Autowired
    StageService stageService;
    @Autowired
    StepService stepService;
    @Autowired
    TitleService titleService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    NeedToBuyService needToBuyService;

    @GetMapping("/stage")
    public ModelAndView stage() {
        ModelAndView modelAndView = new ModelAndView("admin_stage");
        modelAndView.addObject("stages", stageService.list());
        return modelAndView;
    }

    @PostMapping("stage")
    public ModelAndView addStage(Stage stage) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/stage");
        stageService.insert(stage);
        return modelAndView;

    }

    @DeleteMapping("stage/{id}")
    public ModelAndView deleteStage(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/stage");
        stageService.delete(id);
        return modelAndView;

    }

    @PutMapping("stage")
    public ModelAndView putStage(Stage stage) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/stage");
        stageService.update(stage);
        return modelAndView;

    }

    @GetMapping("stage/{id}/edit")
    public ModelAndView editStage(@PathVariable("id") int id) {
        Stage stage = stageService.get(id);
//System.out.println(stage);
        ModelAndView modelAndView = new ModelAndView("admin_edit_stage");
        modelAndView.addObject("stage", stage);

        return modelAndView;
    }

    @GetMapping("stage/{id}/item")
    public ModelAndView item(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("admin_item");
        List<Item> items = itemService.listBySid(id);
        modelAndView.addObject("items", items);
        modelAndView.addObject("sid", id);

        return modelAndView;

    }

    @PostMapping("item")
    public ModelAndView insert_item(Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/stage/" + item.getSid() + "/item");
        itemService.insert(item);
        return modelAndView;
    }

    @DeleteMapping("item/{id}")
    @Transactional
    public ModelAndView del_item(@PathVariable("id") int id) {
        Item item = itemService.get(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/stage/" + item.getSid() + "/item");
        itemService.delete(id);
        if(item.getNeedToBuy() != null){
            needToBuyService.delete(item.getNeedToBuy().getId());
        }
        return modelAndView;

    }

    @PutMapping("item")
    public ModelAndView put_item(Item item) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/stage/" + item.getSid() + "/item");
        itemService.update(item);
        return modelAndView;

    }

    @GetMapping("item/{id}/edit")
    public ModelAndView editItem(@PathVariable("id") int id) {
        Item item = itemService.get(id);
        ModelAndView modelAndView = new ModelAndView("admin_edit_item");
        modelAndView.addObject("item", item);
        return modelAndView;

    }

    @GetMapping("item/{id}/step")
    public ModelAndView step(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("admin_step");
        List<Step> steps = stepService.listByIid(id);
        modelAndView.addObject("steps", steps);
        modelAndView.addObject("iid", id);
        Item item = itemService.get(id);
        modelAndView.addObject("sid", item.getSid());
        return modelAndView;

    }

    @PostMapping("step")
    public ModelAndView insertStep(Step step) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/item/" + step.getIid() + "/step");
        stepService.insert(step);
        return modelAndView;

    }

    @DeleteMapping("step/{id}")
    public ModelAndView delStep(@PathVariable("id") int id) {
        Step step = stepService.get(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/item/" + step.getIid() + "/step");
        stepService.delete(id);
        return modelAndView;

    }

    @PutMapping("step")
    public ModelAndView putStep(Step step) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/item/" + step.getIid() + "/step");
        stepService.update(step);
        return modelAndView;
    }

    @GetMapping("step/{id}/edit")
    public ModelAndView editStep(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("admin_edit_step");

        Step step = stepService.get(id);
        modelAndView.addObject("step", step);
        return modelAndView;
    }

    @GetMapping("step/{id}/title")
    public ModelAndView title(@PathVariable("id") int id) {
        List<Title> titles = titleService.listByStid(id);
        ModelAndView modelAndView = new ModelAndView("admin_title");
        modelAndView.addObject("titles", titles);
        modelAndView.addObject("stid", id);
        return modelAndView;
    }

    @PostMapping("title")
    public ModelAndView insertTitle(Title title) {
        titleService.insert(title);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/step/" + title.getStid() + "/title");
        return modelAndView;
    }

    @DeleteMapping("title/{id}")
    public ModelAndView delTitle(@PathVariable("id") int id) {
        Title title = titleService.get(id);
        titleService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/step/" + title.getStid() + "/title");
        return modelAndView;

    }

    @PutMapping("title")
    public ModelAndView putTitle(Title title) {
        titleService.update(title);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/step/" + title.getStid() + "/title");
        return modelAndView;

    }

    @GetMapping("title/{id}/edit")
    public ModelAndView editTitle(@PathVariable("id") int id) {
        Title title = titleService.get(id);
        ModelAndView modelAndView = new ModelAndView("admin_edit_title");
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("title/{id}/content")
    public ModelAndView content(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("admin_content");
        Content content = contentService.getByTid(id);
        //System.out.println(content);
        modelAndView.addObject("content", content);
        return modelAndView;
    }

    @GetMapping("title/{id}/content/insert")
    public ModelAndView insertContentPage(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("admin_content_add");
        modelAndView.addObject("tid", id);

        return modelAndView;
    }

    @PostMapping("content")
    public ModelAndView insertContent(Content content) {
        contentService.insert(content);
        return new ModelAndView("redirect:/admin/title/" + content.getTid() + "/content");
    }

    @DeleteMapping("content/{id}")
    public ModelAndView delContent(@PathVariable("id") int id) {
        Content content = contentService.get(id);
        //  System.out.println(content);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/title/" + content.getTid() + "/content");
        contentService.delete(id);
        return modelAndView;


    }

    @GetMapping("step/{id}/review")
    public ModelAndView review(@PathVariable("id") int id) {
        List<Review> reviews = reviewService.listByStid(id);
        ModelAndView modelAndView = new ModelAndView("admin_review");
        modelAndView.addObject("reviews", reviews);
        modelAndView.addObject("stid", id);
        return modelAndView;
    }


    @PostMapping("review")
    public ModelAndView insertReview(Review review) {
        review.setUid(0);
        reviewService.insert(review);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/step/" + review.getStid() + "/review");

        return modelAndView;

    }

    @DeleteMapping("review/{id}")
    public ModelAndView delReview(@PathVariable("id") int id) {
        Review review = reviewService.get(id);

        reviewService.delete(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/step/" + review.getStid() + "/review");

        return modelAndView;
    }

    @GetMapping("user")
    public ModelAndView user() {
        ModelAndView modelAndView = new ModelAndView("admin_user");
        List<User> users = userService.list();
        modelAndView.addObject("users", users);
        return modelAndView;

    }

    @GetMapping("need_to_buy")
    public ModelAndView nbuy() {
        List<NeedToBuy> list = needToBuyService.list();
        ModelAndView modelAndView = new ModelAndView("admin_need_to_buy");
        modelAndView.addObject("needs", list);
        return modelAndView;

    }

    @DeleteMapping("need_to_buy/{id}")
    public ModelAndView delNeed(@PathVariable("id") int id , boolean flag) {
        NeedToBuy needToBuy = needToBuyService.get(id);
        needToBuyService.delete(id);
        if(flag)
        return new ModelAndView("redirect:/admin/need_to_buy");
        else
            return new ModelAndView("redirect:/admin/stage/"+needToBuy.getItem().getSid()+"/item");
    }

    @PostMapping("need_to_buy")
    public ModelAndView addNeed(NeedToBuy needToBuy, boolean flag) {
        needToBuyService.insert(needToBuy);
        System.out.println(needToBuy.getIid());
        if (flag)
            return new ModelAndView("redirect:/admin/need_to_buy");
        else
            return new ModelAndView("redirect:/admin/stage/" + itemService.get(needToBuy.getIid()).getSid() + "/item");
    }

}
