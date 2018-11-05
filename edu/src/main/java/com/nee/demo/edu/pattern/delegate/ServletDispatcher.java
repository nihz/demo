package com.nee.demo.edu.pattern.delegate;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServletDispatcher {

    private List<Handler> handlerMapper = new ArrayList<Handler>();

    public ServletDispatcher() {

        Handler handler = new Handler();

        handlerMapper.add(handler);
    }

    public void doService(HttpServletRequest requet, HttpServletResponse response) {
        doDispatch(requet, response);
    }


    private void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        Handler handler = null;
        for (Handler h : handlerMapper) {
            if (h.getUrl().equals(uri)) {
                handler = h;
                break;
            }
        }
    }

    @Data
    class Handler {
        private Object controller;
        private Method method;
        private String url;
    }

}
