package com.nee.spring.cloud.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
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

    public List<?> serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("anotherservice");
        if (list != null && list.size() > 0 ) {
            return list;
        }
        return discoveryClient.getServices();
    }

    @RequestMapping("/service")
    public List<?> home() {
        log.debug("request for service url");
        System.out.println("hello world");
        registerThings();
        return serviceUrl();
    }

    @RequestMapping("service/{serviceName}")
    public List<?> getServerList(@PathVariable String serviceName) {
        System.out.println(serviceName);
        return discoveryClient.getInstances(serviceName)
                .stream()
                .map(s -> s.getServiceId() + "-" + s.getHost() + ":" + s.getPort())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        new SpringApplicationBuilder(SpringCloudZookeeperDiscoverApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
