package com.nee.spring.cloud.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String applicationName;

    private volatile Set<String> targetUrls = new HashSet<>();

    @Scheduled(fixedRate = 10 * 1000)
    public void updateTargetUrls(){

        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(applicationName);

        Set<String> oldTargetUrls = this.targetUrls;
        Set<String> newTargetUrls =  serviceInstanceList
                .stream()
                .map(s -> s.isSecure()?  "https://" + s.getHost() + ":" + s.getPort() : "http://" + s.getHost() + ":" + s.getPort())
                .collect(Collectors.toSet());

        this.targetUrls = newTargetUrls;
        oldTargetUrls.clear();
    }

    @GetMapping("/invoke/say")
    public String invokeSay(@RequestParam String message) {

        List<String> targetUrls = new ArrayList<>(this.targetUrls);

        int size = targetUrls.size();

        int index = new Random().nextInt(size);

        String targetUrl = targetUrls.get(index);

        return restTemplate.getForObject(targetUrl +"/say?message=" + message, String.class);

    }



    @GetMapping("say")
    public String say(@RequestParam String message) {

        System.out.println("receive message - say: " + message);
        return "hello, " + message;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
