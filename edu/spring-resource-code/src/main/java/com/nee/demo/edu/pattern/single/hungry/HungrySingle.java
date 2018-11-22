package com.nee.demo.edu.pattern.single.hungry;

public class HungrySingle {

    private HungrySingle() {

    }

    private static final HungrySingle hungry = new HungrySingle();

    public static HungrySingle getInstance() {


        return hungry;
    }

}
