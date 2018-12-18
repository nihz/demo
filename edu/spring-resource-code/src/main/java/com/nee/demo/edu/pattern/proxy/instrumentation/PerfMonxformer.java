package com.nee.demo.edu.pattern.proxy.instrumentation;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class PerfMonxformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        System.out.printf("transforming: %s \n", className);
        try {
            ClassPool cl = ClassPool.getDefault();
            CtClass clazz = cl.makeClass(new ByteArrayInputStream(classfileBuffer));
            if (!clazz.isInterface()) {
                CtMethod[] methods = clazz.getMethods();
               for (CtMethod method : methods) {
                   method.insertBefore("long time = System.currentTimeMillis();");
                   method.insertBefore("System.out.println(\"leaving \"" + method.getName() + "and cost time: \" + (System.currentTimeMillis() - time));");
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
