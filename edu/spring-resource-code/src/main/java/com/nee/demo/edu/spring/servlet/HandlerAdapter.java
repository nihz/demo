package com.nee.demo.edu.spring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HandlerAdapter {


    private Map<String, Integer> paramsMappings;

    public HandlerAdapter(Map<String, Integer> paramsMappings) {
        this.paramsMappings = paramsMappings;
    }

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, HandlerMapping handler) {

        // method 方法的参数列表
        Class[] paramsTypes = handler.getMethod().getParameterTypes();

        Map<String, String[]> reqParamMap = req.getParameterMap();


        Object[] paramValues = new Object[paramsTypes.length];

        reqParamMap.forEach((k,v) -> {
            String value = Arrays.toString(v).replaceAll("\\[|\\]", "").replaceAll("\\s", ",");

            paramValues[this.paramsMappings.get(k)] = value;

        });

        int reqIndex = this.paramsMappings.get(HttpServletRequest.class.getName());
        paramValues[reqIndex] = req;

        int respIndex = this.paramsMappings.get(HttpServletResponse.class.getName());
        paramValues[respIndex] = resp;

        try {
            Object result = handler.getMethod().invoke(handler.getController(), paramValues);
            boolean isModelAndView = handler.getMethod().getReturnType() == ModelAndView.class;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
