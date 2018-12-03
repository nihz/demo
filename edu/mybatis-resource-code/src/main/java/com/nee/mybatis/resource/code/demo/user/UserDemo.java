package com.nee.mybatis.resource.code.demo.user;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserDemo {

    public static void main(String[] args) throws FileNotFoundException {
        UserDemo userDemo = new UserDemo();
        SqlSession sqlSession = userDemo.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setName("heikki");
        user.setSex(UserSexEnum.F);
        // userMapper.insert(user);
        user = userMapper.selectByPrimaryKey(1l);
        // sqlSession.commit();

        UserMapper userMapper2 = userDemo.getSqlSession().getMapper(UserMapper.class);
        User nUser = new User();
        nUser.setName("heikki2");
        nUser.setId(1l);
        userMapper2.update(nUser);
        System.out.println(user);
        user = userMapper.selectByPrimaryKey(5l);
        System.out.println(user);



    }

    public  SqlSession getSqlSession() throws FileNotFoundException {

        InputStream configFile = new FileInputStream("E:\\work\\ws\\demo\\edu\\mybatis-resource-code\\src\\main\\resources\\mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configFile);

        return sqlSessionFactory.openSession();
    }
}
