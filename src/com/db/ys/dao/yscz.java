package com.db.ys.dao;
import com.alibaba.druid.pool.DruidDataSource;
import com.db.ys.bean.ysl;
import com.db.ys.utils.druid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class yscz {
    public yscz (){}

    public ysl getys(int i){
        DruidDataSource ds;
        ds=new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("ys");
        ds.setUrl("jdbc:mysql://23.94.7.165:3306/ys?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        ds.setPassword("123456");
        ysl ys=new ysl();
        try {
            QueryRunner qr=new QueryRunner(ds);
            ys=qr.query("SELECT * FROM `ysb` WHERE `id` = 20309",new BeanHandler<ysl>(ys.getClass()));

        return ys;
    } catch (SQLException e) {
            e.printStackTrace();
        }
        return ys;
    }
    public List<ysl> sysj(){

        QueryRunner qr=new QueryRunner();
        List<ysl> yslb=new ArrayList<ysl>();
        try {
            yslb=qr.query(druid.getcon(),"SELECT * FROM `ysb` WHERE `lx` LIKE '%动作片%'AND sytime!='未知' ORDER BY `sytime` DESC LIMIT 14",new BeanListHandler<>(ysl.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(yslb.size());
        return null;
    }

}
