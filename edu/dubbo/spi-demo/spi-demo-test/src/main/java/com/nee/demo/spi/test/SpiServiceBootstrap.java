package com.nee.demo.spi.test;

import com.nee.demo.spi.service.SpiDemoService;

import java.util.ServiceLoader;

public class SpiServiceBootstrap {

    public static void main(String[] args) {
        ServiceLoader<SpiDemoService> serviceLoader =
                ServiceLoader.load(SpiDemoService.class);

        serviceLoader.forEach(s -> {
            System.out.println(s.connect("localhost"));
        });
    }
}
