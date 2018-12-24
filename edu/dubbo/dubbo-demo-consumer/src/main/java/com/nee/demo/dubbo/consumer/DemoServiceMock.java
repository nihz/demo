package com.nee.demo.dubbo.consumer;

import com.nee.demo.dubbo.api.DemoService;

public class DemoServiceMock implements DemoService {
    @Override
    public String hello() {
        return "no args hello";
    }

    @Override
    public String hello(String name) {
        return "mock";
    }

    @Override
    public String hello2() {
        return "hello2";
    }

    @Override
    public void hello3(String name, int age) {
        System.out.println("hello3");
    }
}
