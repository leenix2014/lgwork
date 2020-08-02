package com.lagou.job3;

import com.alibaba.fastjson.JSONObject;
import com.lagou.util.CuratorUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class MyDatasourePool {
    private static final String PATH = "/db/config";
    private static ComboPooledDataSource dataSourcePool;
    static void initDataSource(byte[] data) throws PropertyVetoException {
        JSONObject prop = JSONObject.parseObject(new String(data));
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass((String) prop.get("c3p0.driverClass"));
        String jdbcUrl = (String) prop.get("c3p0.jdbcUrl");
        dataSource.setJdbcUrl(jdbcUrl);
        System.out.println("初始化数据库连接池，链接地址："+jdbcUrl);
        dataSource.setUser((String) prop.get("c3p0.user"));
        dataSource.setPassword((String) prop.get("c3p0.password"));
        if (testConnection(dataSource)) {
            ComboPooledDataSource lastDatasource = dataSourcePool;
            dataSourcePool = dataSource;
            if (lastDatasource != null){
                lastDatasource.close();
            }
        }
    }

    public static boolean testConnection(ComboPooledDataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT VERSION()");
             ResultSet rs = preparedStatement.executeQuery();){
            while (rs.next()){
                System.out.println("数据库连接测试通过，数据库版本为："+rs.getString(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        JSONObject prop = new JSONObject();
        prop.put("c3p0.driverClass", "com.mysql.cj.jdbc.Driver");
        prop.put("c3p0.jdbcUrl", "jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
        prop.put("c3p0.user", "root");
        prop.put("c3p0.password", "root");
        byte[] data = prop.toString().getBytes();
        CuratorUtils.setData(PATH, data);

//        try {
//            initDataSource(data);
//        } catch (PropertyVetoException e) {
//            e.printStackTrace();
//        }
        CuratorUtils.watchData(PATH, new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("数据库配置发生变化，重新初始化连接池");
                initDataSource(CuratorUtils.getData(PATH));
            }
        });
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
