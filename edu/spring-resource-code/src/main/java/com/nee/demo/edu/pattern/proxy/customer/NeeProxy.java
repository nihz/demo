package com.nee.demo.edu.pattern.proxy.customer;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NeeProxy {



    public static Object newProxyInstance(NeeClassLoader classLoader, Class<?>[] interfaces, NeeInvocationHandler handler) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        //1、动态生成源代码.java文件
        String src = generaterSrc(interfaces);
        String filePath = NeeProxy.class.getResource("").getPath();
        System.out.println(filePath);
        File f = new File(filePath + "$Proxy0.java");
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager =  compiler.getStandardFileManager(null, null, null);
        Iterable iterable = manager.getJavaFileObjects(f);
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
        task.call();
        try {
            manager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class proxyClass = classLoader.findClass("$Proxy0");
        Constructor c = proxyClass.getConstructor(NeeInvocationHandler.class);
       return c.newInstance(handler);
    }

    private static String generaterSrc(Class<?>[] interfaces) {

        StringBuffer sb = new StringBuffer();
        sb.append("package com.nee.demo.edu.pattern.proxy.customer;\r\n");
        sb.append("import java.lang.reflect.Method; \r\n");
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{\r\n" );
        sb.append("NeeInvocationHandler h; \r\n");
        sb.append("public $Proxy0(NeeInvocationHandler h) { \r\n");
        sb.append("this.h = h;} \r\n");
        for (Method m : interfaces[0].getMethods()) {
            sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "() {\r\n");
            sb.append("try { h.invoke(this, " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\"), null);} catch(Throwable e) {} }\r\n");
        }
        sb.append(" }");
        return sb.toString();
    }





}
