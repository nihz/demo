package com.nee.demo.distributed.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.LinkedBlockingDeque;

public class RpcNettyClient {
    private static final String IP = "localhost";
    private static final int PORT = 6666;
    private static final LinkedBlockingDeque<InvokeRequest> pendings = new LinkedBlockingDeque();

    private static final EventLoopGroup GROUP = new NioEventLoopGroup();

    private final Bootstrap bootstrap;

    RpcNettyClient() {
        bootstrap = new Bootstrap()
                .group(GROUP)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {

                        ChannelPipeline pp = channel.pipeline();
                        pp.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pp.addLast("frameEncoder", new LengthFieldPrepender(Integer.MAX_VALUE, 4));
                        pp.addLast("decoder", new StringDecoder());
                        pp.addLast("encoder", new StringEncoder());
                        pp.addLast("handler", new ChannelInboundHandlerAdapter());

                    }
                });
    }


    public void sendRequest(InvokeRequest invokeRequest) {

        try {
            ChannelFuture future = bootstrap.connect(IP, PORT).sync();
            pendings.add(invokeRequest);
            future.channel().writeAndFlush(invokeRequest.getPacket());
        } catch (InterruptedException e) {
            e.printStackTrace();
            pendings.remove(invokeRequest);
        }
    }

    public void handleResult(Object msg) {
        if (pendings.isEmpty()) {
            throw new RuntimeException("x");
        }
        InvokeRequest invokeRequest = pendings.getFirst();
        invokeRequest.setResult(msg);
        invokeRequest.getCountDownLatch().countDown();
    }
}
