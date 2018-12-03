package com.nee.mybatis.resource.code;

import com.nee.mybatis.resource.code.demo.user.UserMapper;

public class NeeEntry {

    public static void main(String[] args) {
        NeeSqlSession sqlSession = new NeeSqlSession(new NeeConfiguration(), new NeeSimpleExecutor());

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        userMapper.selectByPrimaryKey(1l);

    }
}
