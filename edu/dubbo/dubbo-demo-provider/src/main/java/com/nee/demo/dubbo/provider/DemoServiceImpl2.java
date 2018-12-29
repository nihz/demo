package com.nee.demo.dubbo.provider;

import com.nee.demo.dubbo.api.DemoService;

public class DemoServiceImpl2 implements DemoService {
    @Override
    public String hello() {
        return null;
    }

    @Override
    public String hello(String name) {
        return name + ", call dubbo service, version 2";
    }

    @Override
    public String hello2() {
        return null;
    }

    @Override
    public void hello3(String name, int age) {

    }
}
