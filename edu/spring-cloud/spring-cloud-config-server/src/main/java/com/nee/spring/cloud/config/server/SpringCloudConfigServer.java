package com.nee.spring.cloud.config.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServer {

    public static void main(String args[]) {
        SpringApplication.run(SpringCloudConfigServer.class, args);
    }


    @Bean
    public EnvironmentRepository environmentRepository() {
        return (application, profile, label) -> {

            Environment environment = new Environment ("default", profile);


            List<PropertySource> propertySources = environment.getPropertySources();


            Map<String, String> map = new HashMap<>();
            map.put("name", "heikki");
            PropertySource propertySource = new PropertySource("map", map);
            propertySources.add(propertySource);
            return environment;

        };
    }
}
