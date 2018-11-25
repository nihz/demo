# Spring boot



## spring boot mvc rest



所有的http自描述消息处理器均在messageConverters（HttpMessageConverters）,这个集合会传递到RequestMappingHandlerAda，最终控制输出



messageConverters 其中包含很多自描述消息类型的处理，比如Json、xml、text



按照messageConverters中的顺序进行匹配，，



两类方法比较重要

1. read*
2. write*



**自定义转换器**

1. 实现AbstractHttpMessageConverter

   ```java
   public class PropertiesPersonHttpMessageConverter extends
           AbstractHttpMessageConverter<Person> {
   
       public PropertiesPersonHttpMessageConverter() {
           super(MediaType.valueOf("application/properties+person"));
           setDefaultCharset(CharsetUtil.UTF_8);
       }
       @Override
       protected boolean supports(Class<?> clazz) {
           return clazz.isAssignableFrom(Person.class);
       }
       @Override
       protected Person readInternal(Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
           InputStream inputStream = inputMessage.getBody();
           Properties properties = new Properties();
           properties.load(new InputStreamReader(inputStream, getDefaultCharset()));
           Person person = new Person();
           person.setId(Long.valueOf(properties.getProperty("properties.id")));
           person.setName(properties.getProperty("properties.name"));
           return person;
       }
   
       @Override
       protected void writeInternal(Person person, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
           OutputStream outputStream = outputMessage.getBody();
           Properties properties = new Properties();
           properties.setProperty("person.id", String.valueOf(person.getId()));
           properties.setProperty("person.name", String.valueOf(person.getName()));
           properties.store(new OutputStreamWriter(outputStream, getDefaultCharset()), "");
       }
   }
   ```



* @RequestMapping 中的consumes 对应请求头“Content-Type”
* @RequestMapping 中的produces对应请求头“Accept”



HttpMessageConverters执行逻辑：

 * 读操作：尝试是否能读取，canRead 如果返回true，则能够读取
 * 写操作：尝试是否能写入，canWrite 如果返回true，则能够写入



## Spring MVC 异常处理

可以查看 servlet3.1规范 



### 理解传统servlet  web.xml 错误页面

<error-page> 处理逻辑：

* 处理状态码 ：<error-code>
* 处理异常类型：<exception-type>
* 处理服务：<location>



### Spring Web MVC 异常处理

* @ExceptionHandler
* @RequestControllerAdviser = @ControllerAdviser + @ResponseBody
* @ControllerAdviser

### SpringBoot 错误处理页面

Application实现ErrorPageRegistrar接口，并实现 registerErrorPages 方法

```
@Override
public void registerErrorPages(ErrorPageRegistry registry) {
    registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
}
```





## 视图技术

### view

#### render 方法

``` java
void render(@Nullable Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
      throws Exception;
```

处理页面渲染的逻辑，例如：Velocity、jsp、Thymeleaf

### ViewResolver

​	= 页面　 +　解析器

通过resolverViewName 寻找合适的view对象



**请求的执行逻辑**：

*RequestURI -> RequestMappingHandlerMapping - > HandlerMethod -> return 'viewName' -> (完整的页面名称 = prefix + “viewName" + suffix) -> viewResolver -> View -> render -> HTML*



#### Thymeleaf

自动装配类

```java
ThymeleafAutoConfiguration
```



``` java
ContentNegotiatingViewResolver
```

用于处理多个 viewResolver 



## Spring Boot Jdbc

> 题外话：在Spring Boot 2.0.0， 
>
> 如果应用采用Spring WEB MVC作为Web服务，默认情况下，使用嵌入式Tomcat
>
> 如果应用采用Spring WEB Flux， 默认情况下，使用嵌入式Netty

### 单数据源应用场景

#### 数据连接池技术

Apache Commons DBCP



Tomcat DBCP



#### 事务

* Auto commit mode

* Transaction isolation levels

  * TRANSACTION_READ_UNCOMMITTED
  * TRANSACTION_READ_COMMITTED
  * TRANSACTION_REPEATABLE_READ
  * TRANSACTION_SERIALIZABLE

* 保护点（嵌入式事务）

  * SavePoints

* @Transaction 是由TransactionInterceptor 代理处理
*　也可以通过API实现　使用：PlatformTransactionManager

