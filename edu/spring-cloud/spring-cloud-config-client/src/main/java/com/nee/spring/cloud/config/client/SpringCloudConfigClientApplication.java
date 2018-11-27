package com.nee.spring.cloud.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@SpringBootApplication
public class SpringCloudConfigClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }

    @Configuration
    @Order(HIGHEST_PRECEDENCE)
    public static class MyPropertiesSourceLocator implements PropertySourceLocator {

        @Override
        public PropertySource<?> locate(Environment environment) {
            Map<String, Object > source = new HashMap<>();
            source.put("server.port", 9090);
            MapPropertySource mapPropertySource = new MapPropertySource("my-property-source", source);
            return mapPropertySource;
        }
    }
}


