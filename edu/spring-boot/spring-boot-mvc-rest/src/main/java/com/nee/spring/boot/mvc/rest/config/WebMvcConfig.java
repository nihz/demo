package com.nee.spring.boot.mvc.rest.config;

import com.nee.spring.boot.mvc.rest.http.message.PropertiesPersonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configurable
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new MappingJackson2HttpMessageConverter());
        // converters.add(new PropertiesPersonHttpMessageConverter());
    }
}
