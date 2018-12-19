package com.nee.demo.distributed.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RpcNettyClient {
    private static final String IP = "localhost";
    private static final int PORT = 6666;
    // private static final LinkedBlockingDeque<InvokeRequest> pendings = new LinkedBlockingDeque();
    private static final ConcurrentHashMap<Integer, InvokeRequest> pendings = new ConcurrentHashMap(8);

    private static final EventLoopGroup GROUP = new NioEventLoopGroup();

    private final Bootstrap bootstrap;

    RpcNettyClient() {
        bootstrap = new Bootstrap()
                .group(GROUP)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {

                        ChannelPipeline pipeline = channel.pipeline();
                        /*pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(Integer.MAX_VALUE, 4));
                        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));*/
                        pipeline.addLast(
                                new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this
                                        .getClass().getClassLoader())));
                        // 设置发送消息编码器
                        pipeline.addLast(new ObjectEncoder());
                        pipeline.addLast("handler", new RpcClientHandler(RpcNettyClient.this));
                    }
                });
    }


    public void sendRequest(InvokeRequest invokeRequest) {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
                ChannelFuture f = bootstrap.connect(invokeRequest.getHost(), invokeRequest.getPort()).sync();
                pendings.put(invokeRequest.getPacket().getXid(), invokeRequest);
                f.channel().writeAndFlush(invokeRequest.getPacket());
                f.channel().closeFuture();
        } catch (Exception e) {
            log.error("client exception: {}", e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }

    public void handleResult(Object msg) {
        if (pendings.isEmpty()) {
            throw new RuntimeException("x");
        }
        Packet packet = (Packet) msg;
        InvokeRequest invokeRequest = pendings.get(packet.getXid());
        invokeRequest.setResult(packet.getResult());
        invokeRequest.getCountDownLatch().countDown();
    }


}
