package com.nee.spring.cloud.hystrix;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class SpringCloudHystrixApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudHystrixApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}