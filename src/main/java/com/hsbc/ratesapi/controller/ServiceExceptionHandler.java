package com.hsbc.ratesapi.controller;

import com.hsbc.ratesapi.service.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ServiceException.class })
    public ModelAndView handleServiceException(
            ServiceException ex, WebRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }




}
