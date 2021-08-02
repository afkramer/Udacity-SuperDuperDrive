package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/perform_logout")
public class LogoutController {
    @PostMapping()
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/login";
    }
}
