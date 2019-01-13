package com.nee.demo.interview;

import java.lang.reflect.Field;

public class IntegerDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Integer a = 1, b = 2;
        System.out.println("Before: a=" + a + ", b=" + b);
        swap(a, b);
        System.out.println("After: a=" + a + ", b=" + b);
    }

    private static void swap(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer tmp = new Integer(a);
        field.set(a, b);
        field.set(b, tmp);

    }

}
