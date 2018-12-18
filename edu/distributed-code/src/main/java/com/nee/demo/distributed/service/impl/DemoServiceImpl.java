package com.nee.demo.distributed.service.impl;

import com.nee.demo.distributed.service.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String hello(String name) {

        return "rpc return: " + name;
    }
}
