package com.nee.demo.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DubboProviderTest {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("dubbo-provider.xml");
        classPathXmlApplicationContext.start();

        System.in.read();
    }
}
