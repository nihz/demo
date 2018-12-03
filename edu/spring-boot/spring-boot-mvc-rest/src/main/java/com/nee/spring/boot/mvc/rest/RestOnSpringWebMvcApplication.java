package com.nee.spring.boot.mvc.rest;

import ch.qos.logback.core.status.ErrorStatus;
import com.nee.spring.boot.mvc.rest.interceptor.DefaultHandlerInterceptor;
import org.apache.coyote.ErrorState;
import org.springframework.boot.WebApplicationType;dd
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class RestOnSpringWebMvcApplication implements WebMvcConfigurer, ErrorPageRegistrar {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RestOnSpringWebMvcApplication.class).web(true).run(args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultHandlerInterceptor());
    }


    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
    }
}
