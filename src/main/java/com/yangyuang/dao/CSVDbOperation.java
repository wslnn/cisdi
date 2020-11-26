package com.yangyuang.dao;

import com.yangyuang.beans.DbBeans;
import com.yangyuang.entity.CSVData;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CSVDbOperation {

    Connection conn;

    public CSVDbOperation(Connection conn) {
        this.conn = conn;
    }


    public boolean add2CSVData(String filePath) throws IOException, SQLException {
        File file = new File(filePath);
        return add2CSVData(file);
    }

    /**
     * 将文件存入数据库表csvdata中，主键为文件名
     * @param file 待存入文件
     * @return 是否成功
     * @throws IOException
     * @throws SQLException
     */
    public boolean add2CSVData(File file) throws IOException, SQLException {
        FileInputStream fi = new FileInputStream(file);
        String sql = "insert into csvdata values(?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,file.getName());
        statement.setBlob(2,fi);
        int count = statement.executeUpdate();
        return count != 0;
    }


    /**
     * 通过文件名从数据库中读取原始csv数据，数据库中主键为文件带后缀名名称
     * @param name 带后缀的文件名，如testdata.csv
     * @return 封装文件名称和文件二进制数据的对象
     * @throws IOException
     * @throws SQLException
     */
    public CSVData getCSVFromDb(String name) throws IOException, SQLException {
        String sql = "select * from csvdata where name = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,name);
        ResultSet resultSet = statement.executeQuery();
        CSVData csvData = null;
        while (resultSet.next()){
            csvData = new CSVData();
            csvData.setData(resultSet.getString("data").getBytes());
            csvData.setName(resultSet.getString("name"));
        }
        return csvData;
    }

    public static void main(String[] args) throws IOException, SQLException {
        String path = "D:\\Documents\\WeChat Files\\wxid_87qi65ssv6i852\\FileStorage\\File\\2020-11\\Java测试题\\testdata.csv";
//        String path = "D:\\Documents\\GitHub\\cisdi\\src\\main\\resources\\generatorConfig.xml";
//        File file = new File(path);
//        byte[] data = readFromFile(file);
//        new CSV(DbBeans.conn).add2CSVData(path);
//        System.out.println("haha");
        File file = new File(path);
        System.out.println(new CSVDbOperation(DbBeans.conn).getCSVFromDb(file.getName()));
//        query();
    }
}
