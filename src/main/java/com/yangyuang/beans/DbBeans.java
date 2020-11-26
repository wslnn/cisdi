package com.yangyuang.beans;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

//TODO 提取参数至配置文件
public class DbBeans {
    public static DriverManagerDataSource dataSource;
    public static Connection conn;
    static {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://39.101.191.131:3306/demo?characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        try {
            conn = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
