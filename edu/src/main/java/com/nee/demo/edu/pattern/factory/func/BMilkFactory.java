package com.nee.demo.edu.pattern.factory.func;

import com.nee.demo.edu.pattern.factory.BMilk;
import com.nee.demo.edu.pattern.factory.Milk;

public class BMilkFactory implements Factory{
    public Milk getMilk() {
        return new BMilk();
    }
}
