package com.nee.mybatis.resource.code;

import com.nee.mybatis.resource.code.demo.user.UserMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class NeeMapperProxy implements InvocationHandler {

    private NeeSqlSession sqlSession;

    public NeeMapperProxy(NeeSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().getName().equals(NeeConfiguration.UserMapperXml.namespace)) {
           String sql = NeeConfiguration.UserMapperXml.methodSqlMapping.get(method);

           return sqlSession.selectOne(sql, args[0].toString());
        }
        return method.invoke(this, args);
    }
}
