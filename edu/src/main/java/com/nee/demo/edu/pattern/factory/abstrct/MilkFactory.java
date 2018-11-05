package com.nee.demo.edu.pattern.factory.abstrct;

import com.nee.demo.edu.pattern.factory.Milk;
import com.nee.demo.edu.pattern.factory.func.AMilkFactory;
import com.nee.demo.edu.pattern.factory.func.BMilkFactory;

public class MilkFactory extends AbstractFactory {
    public Milk getAMilk() {
        return new AMilkFactory().getMilk();
    }

    public Milk getBMilk() {
        return new BMilkFactory().getMilk();
    }
}
