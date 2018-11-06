package com.nee.demo.edu.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

@Slf4j
public class NioServer {

    private static final int PORT = 5678;

    public static void start() {
        start(PORT);
    }

    private static void start(int port) {

        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("server started, bind port: ", port);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                keys.forEach(key -> {
                    keys.remove(key);
                    new EventHandle(key, selector).run();
                });
            }
        } catch (IOException e) {
            log.error("A error occur while starting server, cause: ", e.getCause());
        }
    }


    public static void main(String[] args) {
        start();


    }

}

class EventHandle extends Thread {
    private SelectionKey key;
    private Selector selector;

    EventHandle(SelectionKey key, Selector selector) {

        this.key = key;
        this.selector = selector;
    }

    @Override
    public void run() {

        if (!this.key.isValid()) return;
        if (this.key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //通过ServerSocketChannel的accept创建SocketChannel实例
            //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
            SocketChannel sc = null;
            try {
                sc = ssc.accept();
                //设置为非阻塞的
                sc.configureBlocking(false);
                //注册为读
                sc.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (this.key.isReadable()) {

        }

    }
}
