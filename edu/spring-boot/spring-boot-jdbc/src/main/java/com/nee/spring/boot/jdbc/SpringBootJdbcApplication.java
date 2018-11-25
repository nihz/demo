package com.nee.spring.boot.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringBootJdbcApplication {

    public static void main(String []  args) {
//        new SpringApplicationBuilder(SpringBootJdbcApplication.class).web(WebApplicationType.REACTIVE).run(args);
        SpringApplication.run(SpringBootJdbcApplication.class, args);
    }
}
