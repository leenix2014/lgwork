package com.lagou.edu.controller;

import com.lagou.edu.interceptor.LoginInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session){
        if (!"admin".equals(password) || !"admin".equals(username)) {
            return "redirect:/";
        }
        session.setAttribute(LoginInterceptor.LOGINED, true);
        return "redirect:/list";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute(LoginInterceptor.LOGINED);
        return "redirect:/";
    }
}
