package com.nee.demo.distributed.rpc;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

@Data
public class InvokeRequest {

    public InvokeRequest(Packet packet) {
        this.packet = packet;
    }

    private String host;
    private int port;
    private Packet packet;
    private CountDownLatch countDownLatch;
    private Object result;
}
