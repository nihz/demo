package com.nee.demo.edu.spring.context;

import com.nee.demo.edu.spring.annotation.Autowired;
import com.nee.demo.edu.spring.annotation.Controller;
import com.nee.demo.edu.spring.annotation.Service;
import com.nee.demo.edu.spring.aop.AopConfig;
import com.nee.demo.edu.spring.beans.BeanDefinition;
import com.nee.demo.edu.spring.beans.BeanPostProcessor;
import com.nee.demo.edu.spring.beans.BeanWrapper;
import com.nee.demo.edu.spring.context.support.BeanDefinitionReader;
import com.nee.demo.edu.spring.context.support.DefaultListableBeanFactory;
import com.nee.demo.edu.spring.core.BeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private BeanDefinitionReader reader;

    private String[] configLocations;

    private Map<String, Object> beanCacheMap = new HashMap<>();



    private Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<>();

    public ApplicationContext(String... locations) {
        configLocations = locations;
        this.refresh();
    }

    public void refresh() {
        // 定位
        this.reader = new BeanDefinitionReader(configLocations);

        List<String> beanDefinitions = reader.loadBeanDefinitions();

        doRegistry(beanDefinitions);

        doAutowired();
    }

    private void doRegistry(List<String> beanDefinitions) {

        try {
            beanDefinitions.forEach(className -> {
                Class<?> beanClass = null;
                if (beanClass.isInterface()) return;
                BeanDefinition beanDefinition = reader.registerBean(className);
                if (beanDefinition != null)
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);

                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    this.beanDefinitionMap.put(i.getName(), beanDefinition);
                }

            });
        } catch (Exception e) {
            log.error("", e);
        }

    }

    private void doAutowired() {

        this.beanDefinitionMap.forEach((k, v) -> {

            if (!v.isLazyInit()) {
                getBean(k);
            }

        });

    }

    public void populateBean(String beanName, Object instance) {
        Class clazz = instance.getClass();
        if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(Service.class)) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) continue;
            Autowired autowired = field.getAnnotation(Autowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName))
                autowiredBeanName = field.getType().getName();
            field.setAccessible(true);
            try {
                field.set(instance, this.beanWrapperMap.get(autowiredBeanName).getWrapperInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) {

        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);

        String className = beanDefinition.getBeanClassName();

        BeanPostProcessor beanPostProcessor = new BeanPostProcessor();

        Object instance = instantionBean(beanDefinition);
        if (null == instance) return null;

        beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        beanWrapper.setBeanPostProcessor(beanPostProcessor);
        this.beanWrapperMap.put(beanName, beanWrapper);

        beanPostProcessor.postProcessAfterInitialization(instance, beanName);

        populateBean(beanName, instance);
        return this.beanWrapperMap.get(beanName).getWrapperInstance();

    }

    private Object instantionBean(BeanDefinition beanDefinition) {

        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try {

            if (this.beanCacheMap.containsKey(className)) {
                instance = beanCacheMap.get(className);
            } else {
                Class clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.beanCacheMap.put(className, instance);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void refreshBeanFactory() {

    }

    public String[] getBeanDefinitionNames() {

        return this.beanDefinitionMap.keySet().toArray(new String[beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {

        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }

    private AopConfig instantionAopConfig(BeanDefinition beanDefinition) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        AopConfig config = new AopConfig();
        String expression = reader.getConfig().getProperty("pointCut");
        String before = "", after = "" ;

        String className = beanDefinition.getBeanClassName();
        Class clazz = Class.forName(className);
        Pattern pattern = Pattern.compile(expression);
        Class aspectClazz = Class.forName(before);

        for (Method method :clazz.getMethods()) {
            Matcher matcher = pattern.matcher(method.toString());
            if (matcher.matches()) {
                config.put(method, aspectClazz.newInstance(), new Method[]{aspectClazz.getMethod(before), aspectClazz.getMethod(after)});
            }
        }
        return config;
    }

}
