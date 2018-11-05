package com.nee.demo.edu.pattern.observer.subject;

import com.nee.demo.edu.pattern.observer.core.Event;

public class ObserverTest {

    public static void main(String[] args) {
        try {
            Observer observer = new Observer();

            Subject subject = new Subject();

            subject.addListener(SubjectEventType.ON_ADD, observer, Observer.class.getMethod("advice", Event.class));

            subject.add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
