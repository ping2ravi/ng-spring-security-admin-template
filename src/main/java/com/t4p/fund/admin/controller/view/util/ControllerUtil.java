package com.t4p.fund.admin.controller.view.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Component
public class ControllerUtil {

    private final static String DEFAULT_TEMPLATE = "t02";
    @Value("${website.domain:https://www.swarajindia.org}")
    private String websiteDomain;
    @Autowired
    private ResourceUrlProvider resourceUrlProvider;
    private Set<String> validTemplates;

    public ControllerUtil() {
        validTemplates = new HashSet<>();
        validTemplates.add("t01");
        validTemplates.add("t02");
    }

    public void addGenericData(HttpServletRequest httpServletRequest, ModelAndView context) {
        addCurrentUrl(httpServletRequest, context);
        addContextPath(httpServletRequest, context);
        context.getModel().put("resourceUrlProvider", resourceUrlProvider);
    }

    private void addCurrentUrl(HttpServletRequest request, ModelAndView context) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        sb.append(websiteDomain);
        sb.append(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append('?');
            sb.append(queryString);
        }

        context.getModel().put("PAGE_CURRENT_URL", sb.toString());
    }

    private void addContextPath(HttpServletRequest request, ModelAndView context) {
        String contextPath = request.getContextPath();
        context.getModel().put("CONTEXT_PATH", contextPath);
    }

    public String getTemplate(HttpServletRequest request, HttpServletResponse response) {
        String reqTemplate = request.getParameter("template");
        if (reqTemplate != null) {
            response.addCookie(new Cookie("template", "reqTemplate"));
            return reqTemplate;
        }
        if (!validTemplates.contains(reqTemplate)) {
            reqTemplate = DEFAULT_TEMPLATE;
        }

        Cookie[] cookies = request.getCookies();
        for (Cookie oneCookie : cookies) {
            if (oneCookie.getName().equals("template")) {
                return oneCookie.getValue();
            }
        }
        return DEFAULT_TEMPLATE;
    }

}
