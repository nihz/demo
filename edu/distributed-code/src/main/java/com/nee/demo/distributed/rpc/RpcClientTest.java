package com.nee.demo.distributed.rpc;

import com.nee.demo.distributed.service.DemoService;
import com.nee.demo.distributed.service.RpcCallService;

public class RpcClientTest {

    public static void main(String[] args) {

        RpcClient rpcClient = new RpcClient();
        DemoService demoService = (DemoService) rpcClient.acquire(DemoService.class);
        RpcCallService rpcCallService = (RpcCallService) rpcClient.acquire(RpcCallService.class);

        new Thread(() -> System.out.println("rpcCallService" + rpcCallService.call("nee"))).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> System.out.println("demoService" + demoService.hello("nee"))).start();

    }

}
