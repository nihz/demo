package com.nee.demo.edu.spring.servlet;

import com.nee.demo.edu.spring.annotation.*;
import com.nee.demo.edu.spring.aop.ProxyUtils;
import com.nee.demo.edu.spring.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DispatchServlet extends HttpServlet {


    private Properties config = new Properties();

    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    private List<String> classNames = new ArrayList<>();

    private Map<String, HandlerMapping> handlerMapping = new HashMap<>();

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<>();

    private List<ViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replace("/+", "/");
        HandlerMapping handler = handlerMapping.get(url);


        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

        HandlerMapping handler = getHandler(req);
        if (handler == null) {
            log.error("404");
        }

        HandlerAdapter ha = getHandlerAdapter(handler);

        ModelAndView mv = ha.handle(req, resp, handler);

        processDispatchResult(resp, mv);



    }

    private void processDispatchResult(HttpServletResponse resp, ModelAndView mv) {

        if (null == mv) return;
        if (this.viewResolvers.isEmpty()) return;

        this.viewResolvers.forEach(viewResolver -> {
            if (!mv.getViewName().equals(viewResolver.getViewName())) return;


        });
    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {

        if (this.handlerAdapters.isEmpty()) {return null;}

        return this.handlerAdapters.get(handler);
    }

    private HandlerMapping getHandler(HttpServletRequest req) {

        if (this.handlerMappings.isEmpty()) {return null;}
        String url = req.getRequestURI();
        String contextPath =req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if (matcher.matches()) {
                return handlerMapping;
            }
        }

        return null;
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


        ApplicationContext applicationContext = new ApplicationContext();

        initStrategies(applicationContext);

    }

    private void initStrategies(ApplicationContext context) {

        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {
    }

    private void initViewResolvers(ApplicationContext context) {

        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for (File  template: templateRootDir.listFiles()) {
            this.viewResolvers.add(new ViewResolver(template.getName(), template));


        }
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
    }

    private void initHandlerAdapters(ApplicationContext context) {

        this.handlerMappings.forEach(handlerMapping -> {
            Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
            Map<String, Integer> paramMapping = new HashMap<>();


            for (int i = 0; i < pa.length; i++) {
                for (Annotation a : pa[i]) {
                    if (a instanceof RequestParam) {
                        String paramName = ((RequestParam) a).value();
                        if (!"".equals(paramName.trim())) {
                            paramMapping.put(paramName, i);
                        }
                    }
                }
            }

            Class[] paramTypes = handlerMapping.getMethod().getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                Class type = paramTypes[i];
                if (type == HttpServletResponse.class || type == HttpServletResponse.class)
                    paramMapping.put(type.getName(), i);
            }

            this.handlerAdapters.put(handlerMapping, new HandlerAdapter(paramMapping));

        });
    }

    private void initHandlerMappings(ApplicationContext context) {

        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object proxy = context.getBean(beanName);
            Object controller = null;
            try {
                controller = ProxyUtils.getTargetObject(proxy);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Class<?> clazz = controller.getClass();
            if (!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String regex = ("/" + baseUrl + requestMapping.value().replaceAll("/+", "/"));
                Pattern pattern = Pattern.compile(regex);
                this.handlerMappings.add(new HandlerMapping(pattern, proxy, method));
            }
        }
    }

    private void initThemeResolver(ApplicationContext context) {
    }

    private void initLocaleResolver(ApplicationContext context) {
    }

    private void initMultipartResolver(ApplicationContext context) {
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
                    String beanName = service.value();
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
        char[] chars = str.toCharArray();
        chars[0] += 32;

        return chars.toString();
    }

}
