package com.nee.demo.edu.spring.beans;


import com.nee.demo.edu.spring.aop.AopConfig;
import com.nee.demo.edu.spring.aop.AopProxy;
import com.nee.demo.edu.spring.core.FactoryBean;
import lombok.Data;

@Data
public class BeanWrapper extends FactoryBean {

    AopProxy aopProxy = new AopProxy();

    private Object wrapperInstance;
    private Object originalInstance;
    private BeanPostProcessor beanPostProcessor;

    public BeanWrapper(Object instance) {

        wrapperInstance = aopProxy.getProxy(instance);
        originalInstance = instance;
    }

    public void setAopConfig(AopConfig config) {

        aopProxy.setConfig(config);
    }


    public Class getWrapperedClass() {

        return this.wrapperInstance.getClass();
    }
}
