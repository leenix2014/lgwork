package com.lagou.job3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lagou.util.CuratorUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MyDatasourePool {
    private static final String PATH = "/db/config";
    private static ComboPooledDataSource dataSource;
    static {
        JSONObject prop = new JSONObject();
        prop.put("c3p0.driverClass", "com.mysql.cj.jdbc.Driver");
        prop.put("c3p0.jdbcUrl", "jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
        prop.put("c3p0.user", "root");
        prop.put("c3p0.password", "root");

//        byte[] data = new byte[]{};
//        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
//             ObjectOutputStream out = new ObjectOutputStream(bos);){
//            out.writeObject(prop);
//            out.flush();
//            data = bos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        CuratorUtils.setData(PATH, prop.toString().getBytes());
    }

    static void initDataSource() throws PropertyVetoException {
        byte[] data = CuratorUtils.getData(PATH);
        JSONObject prop = JSONObject.parseObject(new String(data));
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
//             ObjectInputStream in = new ObjectInputStream(bis);){
//            prop = (JSONObject) in.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass((String) prop.get("c3p0.driverClass"));
        String jdbcUrl = (String) prop.get("c3p0.jdbcUrl");
        dataSource.setJdbcUrl(jdbcUrl);
        System.out.println("初始化数据库连接池，链接地址："+jdbcUrl);
        dataSource.setUser((String) prop.get("c3p0.user"));
        dataSource.setPassword((String) prop.get("c3p0.password"));
    }

    public static void testConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT VERSION()");
             ResultSet rs = preparedStatement.executeQuery();){
            while (rs.next()){
                System.out.println("数据库连接测试通过，数据库版本为："+rs.getString(1));
            }
        }
    }

    public static void main(String[] args) throws PropertyVetoException, SQLException {
        initDataSource();
        testConnection();
    }
}
