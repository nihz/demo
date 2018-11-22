package com.nee.demo.edu.pattern.proxy.stati;

public class Father {

    Son son;

    public Father(Son son) {
        this.son = son;
    }

    public void findLove() {
        System.out.println("start");
        this.son.findLove();
        System.out.println("end");
    }
}
