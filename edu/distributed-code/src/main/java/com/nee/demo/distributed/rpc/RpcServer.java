package com.nee.demo.distributed.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcServer {

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
        this.port = port;
    }

    public void publish() {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(BOOS_GROUP, WORK_GROUP)
                .channel(NioServerSocketChannel.class);

    }


}
