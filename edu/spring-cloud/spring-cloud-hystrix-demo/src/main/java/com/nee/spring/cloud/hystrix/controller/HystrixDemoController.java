package com.nee.spring.cloud.hystrix.controller;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HystrixDemoController {

    @GetMapping("hello-world")
    @HystrixCommand(fallbackMethod = "errorContent")
    public String helloWord() {
        return "hello, world";
    }

    private String errorContent() {
        return "fall";
    }


    @GetMapping("hello-world2")
    public String helloWord2() {
        return new HelloWorldCommand().execute();

    }
    private static class HelloWorldCommand extends com.netflix.hystrix.HystrixCommand<String> {

        protected HelloWorldCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("Hello, World"), 100);

        }

        @Override
        protected String run() throws Exception {
            int value = new Random().nextInt(200);
            System.out.println("hello, word costs " + value + "ms");
            Thread.sleep(value);
            return "hello, world";
        }

        protected String getFallback() {
            return "errorContent";
        }
    }
}
