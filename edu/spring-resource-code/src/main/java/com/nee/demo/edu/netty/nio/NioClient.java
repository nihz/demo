package com.nee.demo.edu.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class NioClient {

    protected static final String IP = "127.0.0.1";
    protected static final int PORT = 4567;
    private static SocketChannel socketChannel;
    private static Selector selector;

    private static void start() {
        start(IP, PORT);
    }

    private static void start(String ip, int port) {
        try {
            selector = Selector.open();
            connect(ip, port);
            new Thread(() -> {
                while (true) {
                    //无论是否有读写事件发生，selector每隔1s被唤醒一次
                    try {
                        selector.select(1000);
                    } catch (IOException e) {
                        log.error("selector select error, cause: {}", e.getMessage());
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    keys.forEach(key -> {
                        keys.remove(key);
                        try {
                            new ClientEventHandle(key, selector).run();
                        } catch (Exception e) {
                            if (key != null) {
                                key.cancel();
                                if (key.channel() != null) {
                                    try {
                                        key.channel().close();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                }
            }).start();

        } catch (IOException e) {
            log.error("A error occur while start client, error message", e.getMessage());
        }
    }

    protected static void connect(String ip, int port) throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        if (socketChannel.connect(new InetSocketAddress(ip, port))) ;
        else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    public static void main(String[] args) {
        start();

        while (sendMsg(new Scanner(System.in).nextLine())) ;
    }

    private static boolean sendMsg(String msg) {
        sendMessage(msg);
        return true;
    }

    private static void sendMessage(String message) {
        try {
            if (!socketChannel.isConnected()) {
                connect(NioClient.IP, NioClient.PORT);
            }
            // socketChannel.register(selector, SelectionKey.OP_READ);
            //将消息编码为字节数组
            byte[] bytes = message.getBytes();
            //根据数组容量创建ByteBuffer
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            //将字节数组复制到缓冲区
            writeBuffer.put(bytes);
            //flip操作
            writeBuffer.flip();
            //发送缓冲区的字节数组
            socketChannel.write(writeBuffer);

        } catch (ClosedChannelException e) {
            log.error("channel is closed, cause: {}", e.getMessage());
        } catch (IOException e) {
            log.error("write message error, cause: {}", e.getMessage());
        }
    }
}

@Slf4j
class ClientEventHandle extends Thread {
    private SelectionKey key;
    private Selector selector;

    ClientEventHandle(SelectionKey key, Selector selector) {
        this.key = key;
        this.selector = selector;
    }

    @Override
    public void run() {
        if (!key.isValid()) return;

        SocketChannel sc = (SocketChannel) key.channel();
        if (key.isConnectable()) {
            try {
                if (sc.finishConnect()) ;
                else {
                    log.error("client can not connect to server");
                }
                sc.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                log.error("client can not connect to server,  cause: {}", e.getMessage());
            }
        }
        if (key.isReadable()) {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            int byteCount;
            try {
                byteCount = sc.read(bb);
                if (byteCount > 0) {
                    bb.flip();
                    byte[] bytes = new byte[bb.remaining()];
                    bb.get(bytes);
                    String message = new String(bytes);
                    log.info("receive from server: {}", message);
                } else if (byteCount < 0) {
                    key.cancel();
                    sc.close();
                }
            } catch (IOException e) {
                log.info("A error occur while read message from server, error: ", e.getMessage());
            }


        }
    }
}
