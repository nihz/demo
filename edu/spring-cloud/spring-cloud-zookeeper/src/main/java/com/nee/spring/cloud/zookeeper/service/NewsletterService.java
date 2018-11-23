package com.nee.spring.cloud.zookeeper.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface NewsletterService {
    @RequestMapping(method = RequestMethod.GET, value = "/newsletter")
    String getNewsletters();
}