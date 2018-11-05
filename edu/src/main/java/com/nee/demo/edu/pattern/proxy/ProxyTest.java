package com.nee.demo.edu.pattern.proxy;

import com.nee.demo.edu.pattern.proxy.cglib.CglibProxy;
import com.nee.demo.edu.pattern.proxy.customer.NeeProxy;
import com.nee.demo.edu.pattern.proxy.customer.NeeProxyTest;
import com.nee.demo.edu.pattern.proxy.jdk.JDKProxy;
import com.nee.demo.edu.pattern.proxy.stati.Father;
import com.nee.demo.edu.pattern.proxy.stati.Person;
import com.nee.demo.edu.pattern.proxy.stati.Son;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

public class ProxyTest {
    public static void main(String[] args) throws Exception {

//        Father father = new Father(new Son());
//        father.findLove();
//
//
//        Person obj = (Person) new JDKProxy().getInstance(new Son());
//        obj.findLove();
//
//
//
//        Object o = (Son)new CglibProxy().getInstance(Son.class);
//        ((Son) o).findLove();
//
//        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
//        FileOutputStream os = new FileOutputStream("E://$proxy0.class");
//        os.write(bytes);
//
//        os.close();

        Person b = (Person) new NeeProxyTest().getInstance(new Son());
        b.findLove();
    }
}
