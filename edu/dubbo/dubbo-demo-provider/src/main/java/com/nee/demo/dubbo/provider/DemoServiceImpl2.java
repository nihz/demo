package com.nee.demo.dubbo.provider;

import com.nee.demo.dubbo.api.DemoService;

public class DemoServiceImpl2 implements DemoService {
    @Override
    public String hello(String name) {
        return name + ", call dubbo service, version 2";
    }
}
