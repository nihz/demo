package com.nee.demo.distributed.service.impl;

import com.nee.demo.distributed.service.RpcCallService;

public class RpcCallServiceImpl implements RpcCallService {
    @Override
    public String call(String name) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("delay rpc call");

        return "call end";
    }
}
