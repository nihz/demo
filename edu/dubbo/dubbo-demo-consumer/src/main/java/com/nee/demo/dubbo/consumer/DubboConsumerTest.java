package com.nee.demo.dubbo.consumer;

import com.nee.demo.dubbo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumerTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        applicationContext.start();
        DemoService demoService = applicationContext.getBean(DemoService.class);
        System.out.println(demoService.hello("nee"));
    }
}
