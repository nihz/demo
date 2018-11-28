package com.nee.spring.cloud.config.client.controller;

import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    private String myName;

    @GetMapping("myname")
    public String myName() {

        return this.myName;
    }
}
