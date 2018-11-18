package com.nee.demo.edu.spring.context.support;

import com.nee.demo.edu.spring.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BeanDefinitionReader {

    private Properties config = new Properties();

    private List<String> registryBeanClasses = new ArrayList<>();

    private final String SCAN_PACKAGE = "scanPackage";

    public BeanDefinitionReader(String ... locations) {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    public List<String> loadBeanDefinitions() {
        return this.registryBeanClasses;
    }

    public List<String> getRegistyBean() {
        return null;
    }

    public BeanDefinition registerBean(String className) {
        if (this.registryBeanClasses.contains(className)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(lowerFirstCase(className.substring(className.lastIndexOf(".") + 1)));
            return beanDefinition;
        }
        return null;
    }

    private void doScanner(String packageName) {

        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory())
                doScanner(packageName + "." + file.getName());
            else {
                registryBeanClasses.add(packageName + "." + file.getName().replaceAll(".class", ""));
            }
        }
    }

    public Properties getConfig () {

        return config;
    }

    private String lowerFirstCase(String str) {
        char [] chars = str.toCharArray();
        chars[0] += 32;

        return chars.toString();
    }
}
