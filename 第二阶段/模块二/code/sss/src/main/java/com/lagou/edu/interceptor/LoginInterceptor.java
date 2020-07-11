package com.lagou.edu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {

    private static final List<String> allowedUrls = Arrays.asList("/", "/login");

    public static final String LOGINED = "logined";

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        if (allowedUrls.contains(uri)){
            return true;
        }

        HttpSession session = request.getSession(true);
        Object logined = session.getAttribute(LOGINED);
        if (logined != null && (Boolean) logined == true){
            return true;
        }

        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
