package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
// implements HandlerExceptionResolver ??
public class ExceptionController {


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUpload(MaxUploadSizeExceededException ex, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("error", "The maximum file size to upload is 5 MB. Please choose a smaller file.");
        return modelAndView;
    }

    // TODO: handle other cases of things not working -> page-not-found, for example
}
