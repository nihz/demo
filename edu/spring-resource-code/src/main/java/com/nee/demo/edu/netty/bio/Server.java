package com.nee.demo.edu.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {

    private static int DEFAULT_PORT = 7777;

    private static ServerSocket serverSocket;

    public static void start() throws IOException {
        // log.info("server started add port: {}", DEFAULT_PORT);
        start(DEFAULT_PORT);
    }

    public synchronized static void start(int port) throws IOException {
        if (serverSocket != null) return;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    public static void main(String args[]) throws IOException {
        
        start();
    }

}
