package com.nee.demo.edu.netty.ch01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    private static final String IP = "localhost";
    private static final int PORT = 6666;
    private static final int BIZ_GROUP_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int BIZ_THREAD_SIZE = 100;

    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZ_GROUP_SIZE);
    private static final EventLoopGroup workGroup = new NioEventLoopGroup(BIZ_THREAD_SIZE);

    public static void start() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture cf = serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new TcpServerHandler());
                        }
                    }).bind(IP, PORT).sync();
            log.info("server start at ip={}, port={}", IP, PORT);

            cf.channel().closeFuture().sync();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        } catch (Exception e) {

        }
    }

    protected static void shutdown() {
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }


    public static void main(String[] args) {
        log.info("server starting...");
        start();
    }
}
