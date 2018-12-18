package com.nee.demo.distributed.rpc;

import com.nee.demo.distributed.service.DemoService;
import com.nee.demo.distributed.service.impl.DemoServiceImpl;

public class RpcServerTest {
    public static void main(String[] args) {
        DemoService demoService = new DemoServiceImpl();

        new RpcServer().publish(demoService);
    }
}
