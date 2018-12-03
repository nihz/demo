package com.nee.mybatis.resource.code;

public interface NeeExecutor {
    <T> T query(String statement, String parameter);
}
