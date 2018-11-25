package com.nee.spring.boot.jdbc.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MultipleDataSourceConfigure {

    @Bean
    @Primary
    public DataSource masterDataSource() {
        /**
         * spring.datasource.driverName = com.mysql.jdnc.Driver
         * spring.datasource.url = jdbc:mysql://192.168.1.5:3306/fht_finance?characterEncoding=utf8
         * spring.datasource.username = root
         * spring.datasource.password = 123456
         */
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder
                .username("root")
                .password("123456")
                .url("jdbc:mysql://192.168.1.5:3306/test?characterEncoding=utf8")
                .driverClassName("com.mysql.jdbc.Driver").build();

        return dataSource;
    }

    @Bean
    public DataSource slaveDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource dataSource = dataSourceBuilder
                .username("root")
                .password("123456")
                .url("jdbc:mysql://192.168.1.5:3306/test?characterEncoding=utf8")
                .driverClassName("com.mysql.jdbc.Driver").build();

        return dataSource;
    }
}
