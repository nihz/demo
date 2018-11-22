package com.nee.demo.edu.pattern.observer.subject;

import com.nee.demo.edu.pattern.observer.core.EventListener;

public class Subject extends EventListener {
    public void add() {
        System.out.println("add method");
        trigger(SubjectEventType.ON_ADD);
    }

    public void remove() {
        System.out.println("remove method");
        trigger(SubjectEventType.ON_ADD);
    }
}
