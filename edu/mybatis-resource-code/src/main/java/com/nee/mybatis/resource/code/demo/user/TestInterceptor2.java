package com.nee.mybatis.resource.code.demo.user;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class TestInterceptor2 implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("myself2 intercept");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("myself2 intercept plugin");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

        System.out.println("myself2 intercept" + properties);
    }
}
