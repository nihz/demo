package com.nee.demo.dubbo.provider;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DubboProviderTest {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("dubbo-provider.xml");
        classPathXmlApplicationContext.start();

        System.in.read();

        ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("dubbo");
    }
}
