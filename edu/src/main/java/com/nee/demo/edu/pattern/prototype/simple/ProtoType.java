package com.nee.demo.edu.pattern.prototype.simple;

import java.util.ArrayList;
import java.util.List;

public class ProtoType implements Cloneable {
    private String name;

    private List list = new ArrayList();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    public static void main(String[] args) {
        ProtoType p = new ProtoType();

    }
}
