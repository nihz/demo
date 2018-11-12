package com.nee.demo.edu.spring.servlet;

import com.nee.demo.edu.spring.annotation.Autowired;
import com.nee.demo.edu.spring.annotation.Controller;
import com.nee.demo.edu.spring.annotation.Service;
import lombok.val;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static javax.swing.UIManager.put;

public class DispatchServlet extends HttpServlet {


    private Properties config = new Properties();

    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    private List<String> classNames = new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 定位
        doLoadConfig("application-content.properties");

        //扫描
        doScanner(config.getInitParameter("scanPackage"));

        // 注册
        doRegisiter();

        // 自动注入
        doAutowired();

        // 将url和method关联上
        initHandlerMapping();
    }

    private void initHandlerMapping() {
    }

    private void doAutowired() {

        if (beanMap.isEmpty()) return;
        beanMap.forEach((k, v) -> {
            Field[] fields = v.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) continue;
                Autowired autowired = field.getAnnotation(Autowired.class);
                String beanName = autowired.value().trim();
                if ("".equals(beanName))
                    beanName = field.getType().getSimpleName();
                field.setAccessible(true);
                try {
                    field.set(v, beanMap.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doRegisiter() {

        if (classNames.isEmpty()) return;
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);

                // spring中用的是多个方法来处理
                if (clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = lowerFirstCase(clazz.getSimpleName());
                    // Spring 中在这个阶段不会直接put instance, 而是beanDefinition
                    beanMap.put(beanName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);

                    // 默认
                    String beanName = service.name();
                    if ("".equals(beanName.trim()))
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();
                    beanMap.put(beanName, instance);
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class i : interfaces) {
                        beanMap.put(i.getSimpleName(), instance);
                    }
                }


            }
        } catch (ClassNotFoundException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void doScanner(String packageName) {

        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory())
                doScanner(packageName + "." + file.getName());
            else {
                classNames.add(packageName + "." + file.getName().replaceAll(".class", ""));
            }
        }
    }

    private void doLoadConfig(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String lowerFirstCase(String str) {
        char [] chars = str.toCharArray();
        chars[0] += 32;

        return chars.toString();
    }

}
