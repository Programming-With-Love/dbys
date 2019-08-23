package com.db.ys.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class druid {
    private static DruidDataSource ds;
    static {
        ds=new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("ys");
        ds.setUrl("jdbc:mysql://23.94.7.165:3306/ys?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        ds.setPassword("123456");
    }
    public static Connection getcon(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static DruidDataSource getds(){
        return ds;
    }
}
