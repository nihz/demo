package com.nee.spring.boot.mvc.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@RestController // Controller and ResponseBody
public class RestDemoController {


    @GetMapping("404.html")
    public String notFound() {

        return "404";
    }
}
