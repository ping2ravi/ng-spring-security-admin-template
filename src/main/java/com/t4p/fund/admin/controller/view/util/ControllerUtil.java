package com.t4p.fund.admin.controller.view.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import javax.servlet.http.HttpServletRequest;

@Component
public class ControllerUtil {

    @Value("${website.domain:https://www.swarajindia.org}")
    private String websiteDomain;

    @Autowired
    private ResourceUrlProvider resourceUrlProvider;

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
}
