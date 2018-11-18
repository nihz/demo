package com.nee.demo.edu.spring.servlet;


import lombok.Data;

import java.util.Map;

@Data
public class ModelAndView {
    private String viewName;
    private Map<String, ?> model;
}
