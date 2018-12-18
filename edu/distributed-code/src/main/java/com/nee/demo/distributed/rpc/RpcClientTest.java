package com.nee.demo.distributed.rpc;

import com.nee.demo.distributed.service.DemoService;
import com.nee.demo.distributed.service.HelloService;

public class RpcClientTest {

    public static void main(String[] args) {

        DemoService demoService = (DemoService) new RpcClient().acquire(DemoService.class);

        System.out.println(demoService.hello("nee"));
    }
}
