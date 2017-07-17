package com.t4p.fund.admin.controller.view;

import com.t4p.fund.admin.controller.view.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class HomeController {

    @Autowired
    private ControllerUtil controllerUtil;

    @RequestMapping(value = {"/"})
    public ModelAndView home(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        String template = controllerUtil.getTemplate(request, response);
        //make sure template name doesnt start with "/" as it causes issue when run as jar, though it works in editor
        mv.setViewName(template + "/index");
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
        //Sleep method to add soem latency in service so we can see block UI
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
