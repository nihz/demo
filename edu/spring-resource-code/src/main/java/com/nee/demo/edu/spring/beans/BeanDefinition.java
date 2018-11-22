package com.nee.demo.edu.spring.beans;

import lombok.Data;

@Data
public class BeanDefinition {

    private String beanClassName;
    private String factoryBeanName;
    private boolean lazyInit = false;

}
