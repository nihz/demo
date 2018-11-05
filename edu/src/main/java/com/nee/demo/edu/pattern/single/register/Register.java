package com.nee.demo.edu.pattern.single.register;

import java.util.HashMap;
import java.util.Map;

public class Register {
    private Register() {

    }

    public static Map<String, Object> registerMap = new HashMap<String, Object>();

    public static Register getInstance(String name) {

        if (name == null) {
            name = Register.class.getName();
        }
        if (registerMap.get(name) == null) {

                registerMap.put(name, new Register());
        }

        return (Register) registerMap.get(name);
    }
}
