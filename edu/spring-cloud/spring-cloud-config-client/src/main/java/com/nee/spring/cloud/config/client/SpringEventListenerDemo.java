package com.nee.spring.cloud.config.client;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringEventListenerDemo {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext();

        configApplicationContext.addApplicationListener((ApplicationListener<MyApplicationEvent>) event -> System.out.println(event.getSource()));

        configApplicationContext.refresh();
        configApplicationContext.publishEvent(new MyApplicationEvent("Hello, World"));
    }

    private static class MyApplicationEvent extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public MyApplicationEvent(Object source) {
            super(source);
        }


    }
}
