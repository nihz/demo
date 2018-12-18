package com.nee.demo.distributed.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.concurrent.Future;

@Slf4j
public class RpcServer extends Thread {

    private static final Hashtable<String, Object> bindings = new Hashtable<>();
    private static final String IP = "localhost";
    private static final int DEFAULT_PORT = 6666;
    private static final int BOSS_GROUP_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int WORK_GROUP_SIZE = 100;

    private static final EventLoopGroup BOOS_GROUP = new NioEventLoopGroup(BOSS_GROUP_SIZE);
    private static final EventLoopGroup WORK_GROUP = new NioEventLoopGroup(WORK_GROUP_SIZE);

    private int port;

    public RpcServer() {
        this(DEFAULT_PORT);
    }

    public RpcServer(int port) {
        this.start(port);


    }

    public void publish(Object service) {
        synchronized (bindings) {
            bindings.put(service.getClass().getInterfaces()[0].getName(), service);
        }
    }

    private void start(int port) {
        this.port = port;
        super.start();
    }

    @Override
    public void run() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            ChannelFuture future = serverBootstrap.group(BOOS_GROUP, WORK_GROUP)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pp = channel.pipeline();
                            /*pp.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pp.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pp.addLast(new StringEncoder(CharsetUtil.UTF_8));*/
                            pp.addLast(
                                    new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this
                                            .getClass().getClassLoader())));
                            // 设置发送消息编码器
                            pp.addLast(new ObjectEncoder());
                            pp.addLast(new RpcServerHandler(RpcServer.this));
                        }
                    }).bind(port).sync();
            log.info("server start success at ip: {}, port: {}", IP, port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Object lookup(String serviceName) {
        return bindings.get(serviceName);
    }
}
