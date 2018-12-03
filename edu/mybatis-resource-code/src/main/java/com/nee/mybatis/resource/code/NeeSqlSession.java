package com.nee.mybatis.resource.code;

public class NeeSqlSession {

    NeeConfiguration neeConfiguration;
    NeeExecutor neeExecutor;

    public NeeSqlSession(NeeConfiguration neeConfiguration, NeeExecutor neeExecutor) {
        this.neeConfiguration = neeConfiguration;
        this.neeExecutor = neeExecutor;
    }

    public <T> T getMapper(Class<T> clazz) {

       return neeConfiguration.getMapper(clazz, this);
    }

    public <T> T selectOne(String statement, String parameter) {

        return neeExecutor.query(statement, parameter);
    }
}
