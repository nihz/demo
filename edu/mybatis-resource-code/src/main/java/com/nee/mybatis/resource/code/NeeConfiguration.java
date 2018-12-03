package com.nee.mybatis.resource.code;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class NeeConfiguration {
    public <T> T getMapper(Class<T> clazz, NeeSqlSession neeSqlSession) {

        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz},
                new NeeMapperProxy(neeSqlSession));
    }


     static class UserMapperXml {
        static final String namespace = "com.nee.mybatis.resource.code.demo.user.UserMapper";

        static final Map<String, String> methodSqlMapping = new HashMap<>();

        static {
            methodSqlMapping.put("selectByPrimaryKey", "SELECT * FROM users WHERE id = %d");
        }

    }
}
