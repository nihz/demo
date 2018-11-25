# Spring 中的设计模式

* 工厂模式： BeanFactory

* 原型模式： scope 以及apache spring中大多数是反射

* 代理模式： Aop、拦截器、中介，
	* 分为动态代理和静态代理
	* 代理角色、被代理角色
	* 由补代理角色来做最终的决定
	* 代理角色通常持有被代理角色对象引用
	* JDK Proxy的原理 （字节码重组）
		1. 拿到补代理对象的引用，并且获取它的所有接口，反射获取
		2. JDK Proxy 类重新生成一个新的类、同时新的类要实现补代理类所有实现
		3. 动态生成java代码，把新加的业务逻辑方法由一定的代码去调用
		4. 重新生成Java字节码，加载到jvm中运行

* 策略模式： 比较器、旅行路线、固定算法
	* 场景：根据用户的需求处理数据时候 需要对算法做出选择，固定的一些算法
	* 在Spring中 BeanFactory 、ListableBeanFactory

* 模板模式 （通常又叫模板方法 Template Method）
	* JdbcTemplate
* 委派（Delega）
* 适配器（Adapter）
	* 通常采用代理或者继承形式进行包装 has-a
	* 注重兼容、转换
	
* 装饰器（Decorate、wrapper）
	* DataSource、 
	* 为了某个实现类在不修改原始类的基础上进行动态增强方法，
	* 是一种特别的适配器模式，装饰者和被装饰者都要实现同一个接口
	* 注重的是覆盖扩展 is-a