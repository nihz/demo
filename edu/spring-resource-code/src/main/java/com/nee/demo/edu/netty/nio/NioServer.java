package com.nee.demo.edu.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

@Slf4j
public class NioServer {

    private static final int PORT = 4567;
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
            log.info("server started, bind port: {}", port);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                keys.forEach(key -> {
                    keys.remove(key);
                    new EventHandle(key, selector).run();
                });
            }
        } catch (IOException e) {
            log.error("A error occur while starting server, cause: {}", e.getCause());
        }
    }


    public static void main(String[] args) {
        start();
    }

}

@Slf4j
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
            SocketChannel sc;
            try {
                sc = ssc.accept();
                //设置为非阻塞的
                sc.configureBlocking(false);
                //注册为读
                sc.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                log.error("A error occur while accept or register, cause: {}", e.getMessage());
            }

        }
        if (this.key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer bb = ByteBuffer.allocate(1024);
            try {
                int readBytes = sc.read(bb);
                if (readBytes > 0) {
                    bb.flip();
                    byte[] bytes = new byte[bb.remaining()];
                    bb.get(bytes);
                    String r = new String(bytes);
                    log.info("receive message from server: {}", r);
                    response(r, sc);
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            } catch (IOException e) {
                log.error("A error occur while starting server, error message: {}", e.getMessage());
            }
        }
    }

    private void response(String message, SocketChannel sc) {

        try {
            sc.register(selector, SelectionKey.OP_READ);
            byte[] bytes = message.getBytes();
            ByteBuffer bb = ByteBuffer.allocate(bytes.length);
            bb.put(bytes);
            bb.flip();
            sc.write(bb);
            bb.clear();
        } catch (ClosedChannelException e) {
            log.error("register event error, cause: {}", e.getCause());
        } catch (IOException e) {
            log.error("write bb to sc error, cause: {}", e.getMessage());
        }

    }
}
