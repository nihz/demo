package com.nee.demo.dubbo.api;

public interface DemoService {

    String hello();
    String hello(String name);

    String hello2();

    void hello3(String name, int age);
}
