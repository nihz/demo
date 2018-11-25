package com.nee.spring.boot.mvc.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerAdviser {

    @ExceptionHandler(NullPointerException.class)
    public String pageNotFound( HttpServletRequest request,
                                Throwable throwable) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("statusCode", request.getAttribute("javax.servlet.error.status_code"));
        errors.put("requestUri", request.getAttribute("javax.servlet.error.request_uri"));


        return errors.toString();
    }
}
