package com.nee.demo.edu.pattern.factory.func;

import com.nee.demo.edu.pattern.factory.AMilk;
import com.nee.demo.edu.pattern.factory.Milk;

public class AMilkFactory implements Factory {

    public Milk getMilk() {
        return new AMilk();
    }
}
