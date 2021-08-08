package com.udacity.jwdnd.course1.cloudstorage.controller;

// From: https://attacomsian.com/blog/spring-boot-custom-error-page
// Before that my code was still generating whitelabel error pages to the user

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @Override
    public String getErrorPath(){
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = null;

        if (status != null){
            int statusCode = Integer.parseInt(status.toString());

            // change error message according to error type
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                errorMessage = "Sorry, the requested website was not found.";
            }

            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                errorMessage = "Sorry, there was an error processing your request.";
            }

            if(statusCode == HttpStatus.FORBIDDEN.value()){
                errorMessage = "Sorry, you are not allowed to access that page.";
            }
        }

        if(errorMessage == null){
            errorMessage = "Sorry, there was an error.";
        }

        model.addAttribute("error", errorMessage);

        return "error";
    }
}
