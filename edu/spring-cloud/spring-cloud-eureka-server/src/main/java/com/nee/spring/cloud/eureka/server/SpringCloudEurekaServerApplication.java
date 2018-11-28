package com.nee.spring.cloud.eureka.server;

import com.sun.glass.ui.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaServerApplication {

    public static void main(String args[]) {
        SpringApplication application = new SpringApplication(SpringCloudEurekaServerApplication.class);
        application.run(args);
    }
}
