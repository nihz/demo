package com.nee.demo.distributed.rpc;

import com.nee.demo.distributed.service.DemoService;
import com.nee.demo.distributed.service.RpcCallService;
import com.nee.demo.distributed.service.impl.DemoServiceImpl;
import com.nee.demo.distributed.service.impl.RpcCallServiceImpl;

public class RpcServerTest {
    public static void main(String[] args) {
        DemoService demoService = new DemoServiceImpl();
        RpcCallService rpcCallService = new RpcCallServiceImpl();

        RpcServer rpcServer = new RpcServer();
        rpcServer.publish(demoService);
        rpcServer.publish(rpcCallService);
    }
}
