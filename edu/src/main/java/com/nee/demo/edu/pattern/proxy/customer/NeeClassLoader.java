package com.nee.demo.edu.pattern.proxy.customer;

import java.io.*;

public class NeeClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String filePath = NeeProxy.class.getResource("").getPath() + "$Proxy0.class";
        File f = new File(filePath);
        try {
            FileInputStream in = new FileInputStream(f);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = in.read(bytes)) != -1){
                outputStream.write(bytes, 0, length);
            }
            return defineClass(NeeProxy.class.getPackage().getName() + "." + name, outputStream.toByteArray(), 0, outputStream.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }
}
