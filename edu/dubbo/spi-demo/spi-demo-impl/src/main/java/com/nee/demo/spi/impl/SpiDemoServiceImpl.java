package com.nee.demo.spi.impl;

import com.nee.demo.spi.service.SpiDemoService;

public class SpiDemoServiceImpl implements SpiDemoService {
    @Override
    public String connect(String host) {
        return "spi test";
    }
}
