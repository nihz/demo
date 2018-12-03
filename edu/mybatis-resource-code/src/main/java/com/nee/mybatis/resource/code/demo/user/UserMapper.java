package com.nee.mybatis.resource.code.demo.user;

public interface UserMapper {

    User selectByPrimaryKey(Long id);

    int insert(User user);

    int update(User user);
}
