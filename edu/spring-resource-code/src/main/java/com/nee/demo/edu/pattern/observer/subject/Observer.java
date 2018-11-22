package com.nee.demo.edu.pattern.observer.subject;

import com.nee.demo.edu.pattern.observer.core.Event;

public class Observer {

    public void advice(Event e) {
        System.out.println("======= trigger print something ======= \n" +  e);
    }
}
