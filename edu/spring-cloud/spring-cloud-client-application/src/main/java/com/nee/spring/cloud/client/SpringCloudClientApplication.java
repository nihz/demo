package com.nee.spring.cloud.client;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class SpringCloudClientApplication {

    public static void main(String [] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
