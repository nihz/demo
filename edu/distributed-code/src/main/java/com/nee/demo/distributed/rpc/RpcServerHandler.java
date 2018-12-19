package com.nee.demo.distributed.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    private final RpcServer rpcServer;

    public RpcServerHandler(RpcServer rpcServer) {
        super();
        this.rpcServer = rpcServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("channelActive...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        log.info("get exception: {}", cause.getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("server receive message: {}", msg);

        Packet request = (Packet) msg;
        Object instance = rpcServer.lookup(request.getServiceName());

        Method method = instance.getClass().getMethod(request.getMethod(), request.getParamTypes());
        Object result = method.invoke(instance, request.getParams());
        request.setResult(result);
        ctx.channel().writeAndFlush(request);
        ctx.close();
    }
}
