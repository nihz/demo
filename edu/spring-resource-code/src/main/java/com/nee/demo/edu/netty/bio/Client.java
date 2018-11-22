package com.nee.demo.edu.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Client {

    private static int DEFAULT_SERVER_PORT = 7777;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void send(String expression) throws IOException {
        send(DEFAULT_SERVER_PORT, expression);
    }

    private static void send(int port, String expression) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(DEFAULT_SERVER_IP, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(expression);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


    public static void main (String[] args) throws InterruptedException {
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Server.start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        TimeUnit.MICROSECONDS.sleep(100);

        final char[] op = {'+', '-', '*', '/'};
        final Random random = new Random(System.currentTimeMillis());

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    String expression = random.nextInt(10) + "" + op[random.nextInt(4)]  + random.nextInt(10) ;
                    try {
                        Client.send(expression);
                        TimeUnit.MICROSECONDS.sleep(random.nextInt(1000));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
