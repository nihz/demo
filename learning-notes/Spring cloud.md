# Spring cloud 

## Spring Cloud Config

### Spring Cloud Config Server

##### 构建Spring Cloud 配置服务器

实现步骤

1. 在Configuration Class 标记 `@EnableConfigServer`

2. 配置文件目录（基于git）

   1. application.properties （默认）
   2. application-test.properties

3. 服务端配置版本仓库（本地）

   ​	spring.cloud.config.server.git.uri=0

4. 



### Spring Cloud Config Client

java.util.Observable 是订阅者

**@RefreshScope 可以动态的加载bean的属性**

事件监听模式

```java.util.EventObject``` 事件对象

* 事件对象问题关联事件源 source  ```protected transient Object  source;```

```java.util.EventListener```

Spring Boot 初始化的时候加了很多Listener

```java
# Application Listeners
org.springframework.context.ApplicationListener=\
org.springframework.boot.ClearCachesApplicationListener,\
org.springframework.boot.builder.ParentContextCloserApplicationListener,\
org.springframework.boot.context.FileEncodingApplicationListener,\
org.springframework.boot.context.config.AnsiOutputApplicationListener,\
org.springframework.boot.context.config.ConfigFileApplicationListener,\
org.springframework.boot.context.config.DelegatingApplicationListener,\
org.springframework.boot.context.logging.ClasspathLoggingApplicationListener,\
org.springframework.boot.context.logging.LoggingApplicationListener,\
org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener
```

Spring Cloud 事件/监听器

**BootstrapApplicationListener**

* 加载application.properties、application.yml
* 初始化Bootstrap Application ID = “bootstrap”

```java
 ConfigurableApplicationContext context = builder.run();
```

#### Env断点： EnvironmentEndpoint

​	Environment 关联多个

`Environment` 有一两种实现方式

普通：`StandardEnvironment`

Web类型：`StandardServletEnvi`

### 简单对比Eureka

| 比较点     | Eureka                                                 | Zookeeper                | Consul               |
| ---------- | ------------------------------------------------------ | ------------------------ | -------------------- |
| 运维熟悉度 | 相对陌生                                               | 熟悉                     | 更陌生               |
| 一致性     | AP                                                     | CP                       | AP                   |
| 一致性协议 | HTTP定时轮询                                           | ZAB                      | RAFT                 |
| 通讯方式   | HTTP REST                                              | 自定义协议               | HTTP REST            |
| 更新机制   | Peer 2 Peer（服务器之间）+ Scheduler（服务器和客户端） | ZK Watch                 | Agent 监听           |
| 适用规模   | 20k ~ 30K                                              | 10K ~ 20K                | < 3K                 |
| 性能问题   | 简单的更新机制、规模较大GC频繁                         | 扩容麻烦、规模较大GC频繁 | 节点多，更新列表缓慢 |

### 为什么推荐使用ZK作为Spring Cloud的基础设施

* 一致性模型
* 维护相对熟悉



ZK 节点路径（/services/${spring.application-name}/${services_uuid}）





## Spring Cloud负载均衡

### RestTemplate 基本原理

Spring 核心HTPP消息转换器`HttpMessageConverter`

**HttpMessageConverter** 分析



#### 判断是否可读可写

```
public interface HttpMessageConverter<T> {

   boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);

   boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
}
```

#### 当前支持的媒体类型

```
List<MediaType> getSupportedMediaTypes();
```

#### 反序列化

```
T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException;
```

spring 早期就考虑了去servlet ，， HttpInputMessage 类似 HttpServletRequest



RestTemplate 利用 HttpMessageConverter 对媒体类型序列化和反序列化



#### RestTemplate 设计



```
private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
```



```
public RestTemplate() {
   this.messageConverters.add(new ByteArrayHttpMessageConverter());
   this.messageConverters.add(new StringHttpMessageConverter());
   this.messageConverters.add(new ResourceHttpMessageConverter(false));
   try {
      this.messageConverters.add(new SourceHttpMessageConverter<>());
   }
   catch (Error err) {
      // Ignore when no TransformerFactory implementation is available
   }
   this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());

   if (romePresent) {
      this.messageConverters.add(new AtomFeedHttpMessageConverter());
      this.messageConverters.add(new RssChannelHttpMessageConverter());
   }

   if (jackson2XmlPresent) {
      this.messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
   }
   else if (jaxb2Present) {
      this.messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
   }

   if (jackson2Present) {
      this.messageConverters.add(new MappingJackson2HttpMessageConverter());
   }
   else if (gsonPresent) {
      this.messageConverters.add(new GsonHttpMessageConverter());
   }
   else if (jsonbPresent) {
      this.messageConverters.add(new JsonbHttpMessageConverter());
   }

   if (jackson2SmilePresent) {
      this.messageConverters.add(new MappingJackson2SmileHttpMessageConverter());
   }
   if (jackson2CborPresent) {
      this.messageConverters.add(new MappingJackson2CborHttpMessageConverter());
   }

   this.uriTemplateHandler = initUriTemplateHandler();
}
```

* 添加内建HttpMessageConverter
* 根据条件添加第三方库



#### 扩展HTTP客户端

* ClientHttpRequestFactory
  * Spring实现
    * SimpleClientHttpRequestFactory
  * HttpClient
    * HttpComponentsClientHttpRequestFactory
  * OkHttp
    * OkHttp3ClientHttpRequestFactory
    * OkHttpClientHttpRequestFactory



#### RestTemplate整合 Zookeeper

