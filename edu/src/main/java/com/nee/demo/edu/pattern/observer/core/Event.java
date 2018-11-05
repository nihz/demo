package com.nee.demo.edu.pattern.observer.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Method;

@Data
@RequiredArgsConstructor()
@ToString
public class Event {

    private Object source;

    private Object target;

    private Method callback;

    private String trigger;

    public Event(Object target, Method callback) {
        this.target =target;
        this.callback = callback;
    }
}
