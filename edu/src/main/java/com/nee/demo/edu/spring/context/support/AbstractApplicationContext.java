package com.nee.demo.edu.spring.context.support;

public abstract class AbstractApplicationContext {

    protected void onRefresh() {

    }

    protected abstract void refreshBeanFactory();
}
