package com.nee.mybatis.resource.code.demo.user;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private Long id;
    private String name;
    private UserSexEnum sex;
}
