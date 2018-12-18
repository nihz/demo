package com.nee.demo.distributed.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    private final RpcNettyClient rpcNettyClient;

    public RpcClientHandler(RpcNettyClient rpcNettyClient) {
        super();
        this.rpcNettyClient = rpcNettyClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("channelActive...");
        // ctx.writeAndFlush("test");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        log.info("get exception: {}", cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client receive message: {}", msg);

        rpcNettyClient.handleResult(msg);

        ctx.close();
    }
}
