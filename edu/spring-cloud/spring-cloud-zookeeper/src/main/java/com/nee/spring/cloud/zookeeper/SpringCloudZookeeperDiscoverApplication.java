package com.nee.spring.cloud.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@Slf4j
public class SpringCloudZookeeperDiscoverApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

    public void registerThings() {
        ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
                .defaultUriSpec()
                .address("anyUrl")
                .port(10)
                .name("/a/b/c/d/anotherservice")
                .build();
        this.serviceRegistry.register(registration);
    }

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("anotherservice");
        if (list != null && list.size() > 0 ) {
            return list.get(0).getUri().toString();
        }
        return null;
    }

    @RequestMapping("/service-url")
    public String home() {
        log.debug("request for service url");
        System.out.println("hello world");
        registerThings();
        return serviceUrl();
    }

    public static void main(String[] args) {

        new SpringApplicationBuilder(SpringCloudZookeeperDiscoverApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
