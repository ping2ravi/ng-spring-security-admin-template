package com.t4p.fund.admin.controller.view;

import org.springframework.beans    .factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminViewController {

    @Value("${website.domain:https://www.swarajindia.org}")
    private String websiteDomain;

    private String template = "t01";

    @RequestMapping(value = {"/admin/{submenu}/{page}.html"})
    public ModelAndView genericWitHSubmenu(ModelAndView mv, HttpServletRequest request, @PathVariable("submenu") String subMenu, @PathVariable("page") String page) {
        System.out.println("Submenu : " + subMenu);
        System.out.println("page : " + page);
        String templateFile = template + "/admin/" + subMenu + "/" + page;
        return getModelAndView(mv, request, page, templateFile);
    }
    @RequestMapping(value = {"/admin/{page}.html"})
    public ModelAndView generic(ModelAndView mv, HttpServletRequest request, @PathVariable("page") String page) {
        System.out.println("page : " + page);
        String templateFile = template + "/admin/" + page;
        return getModelAndView(mv, request, page, templateFile);
    }

    @RequestMapping(value = {"/admin/{submenu}/{page}"})
    public ModelAndView genericWitHSubmenuWithOutHtmlRewrite(ModelAndView mv, HttpServletRequest request, @PathVariable("submenu") String subMenu, @PathVariable("page") String page) {
        System.out.println("Submenu : " + subMenu);
        System.out.println("page : " + page);
        RedirectView rv = new RedirectView("/");
        mv.setView(rv);
        return mv;
    }
    @RequestMapping(value = {"/admin/{page}"})
    public ModelAndView genericWithoutHtmlRewrite(ModelAndView mv, HttpServletRequest request, @PathVariable("page") String page) {
        System.out.println("page : " + page);
        RedirectView rv = new RedirectView("/");
        mv.setView(rv);
        return mv;
    }
    @RequestMapping(value = {"/{page}"})
    public ModelAndView genericOneLevelPageWithoutHtmlRewrite(ModelAndView mv, HttpServletRequest request) {
        RedirectView rv = new RedirectView("/");
        mv.setView(rv);
        return mv;
    }

    private ModelAndView getModelAndView(ModelAndView mv, HttpServletRequest request, String fragmentName, String fragmentFile) {
        mv.getModel().put("fragmentName", fragmentName);
        mv.getModel().put("fragmentFile", fragmentFile);
        mv.getModel().put("domain", getDomain(request));
        mv.getModel().put("websiteDomain", websiteDomain);

        mv.setViewName(fragmentFile);
        return mv;
    }

    private String getDomain(HttpServletRequest request){
        String domain = request.getScheme() + "://" +   // "http" + "://
                request.getServerName();
        if(request.getServerPort() != 80){
            domain = domain + ":" + request.getServerPort();
        }
        return domain;

    }
}
