package com.nee.demo.edu.pattern.factory.simple;

import com.nee.demo.edu.pattern.factory.AMilk;
import com.nee.demo.edu.pattern.factory.BMilk;
import com.nee.demo.edu.pattern.factory.Milk;

public class SimpleFactory {

    public Milk getAMilk(String name) {
        if ("A".equals(name))
            return new AMilk();
        if ("B".equals(name))
            return new BMilk();
        return null;
    }
}
