package com.t4p.fund.admin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class HomeController {

    private String template = "t01";

    @RequestMapping(value = {"/"})
    public ModelAndView home(ModelAndView mv) {
        mv.setViewName("/t01/index");
        mv.getModel().put("template", template);
        return mv;
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        sleep();
        return user;
    }

    private void sleep()  {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/resource")
    @ResponseBody
    public Map<String, Object> home() {
        sleep();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

}
