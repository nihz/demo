package com.nee.demo.edu.netty.ch01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient implements Runnable {

    private static final String IP = "localhost";
    private static final int PORT = 6666;
    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(Integer.MAX_VALUE, 4));
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast("handler", new  MyClient());
                        }
                    });

            for (int i = 0; i < 1000; i++) {
                ChannelFuture f = bootstrap.connect(IP, PORT).sync();
                f.channel().writeAndFlush(String.format("hello service! ThreadName: {}, : -----> {}", Thread.currentThread().getName(), i));
                f.channel().closeFuture();
            }

        } catch (Exception e) {
            log.error("client exception: {}", e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i< 3; i++) {
            new Thread(new NettyClient(), ">>> this thread " + i).start();
        }
    }
}
