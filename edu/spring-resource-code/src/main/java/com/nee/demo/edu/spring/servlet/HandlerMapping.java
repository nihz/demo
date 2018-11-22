package com.nee.demo.edu.spring.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
public class HandlerMapping {

    private Pattern pattern;
    private Object controller;
    private Method method;

}
