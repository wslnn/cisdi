package com.yangyuang.service.impl;

import com.opencsv.CSVReader;
import com.yangyuang.beans.DbBeans;
import com.yangyuang.dao.CSVDbOperation;
import com.yangyuang.entity.CSVData;
import com.yangyuang.service.CSVService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;


import java.io.*;
import java.sql.SQLException;
import java.util.*;

@Service
public class CSVServiceImpl implements CSVService {

    CSVDbOperation csvDbOperation;
    @Override
    public boolean storeFile2Db(File file) throws IOException, SQLException {
        if (csvDbOperation ==null) {
            csvDbOperation = new CSVDbOperation(DbBeans.conn);
        }
        return csvDbOperation.add2CSVData(file);
    }

    @Override
    public CSVData readFileFromDb(String fileName) throws IOException, SQLException {
        if (csvDbOperation ==null) {
            csvDbOperation = new CSVDbOperation(DbBeans.conn);
        }
        return this.csvDbOperation.getCSVFromDb(fileName);
    }

    @Override
    public void dataAnalysis(InputStreamReader inputStreamReader){
        CSVReader reader = new CSVReader(inputStreamReader);
        Iterator<String[]> iterator = reader.iterator();
        Map<String,List<Double>> cols = new HashMap<>();
        Map<String, Set<String>> factors = new HashMap<>();
        String[] title = iterator.next();
        while (iterator.hasNext()){
            String[] row = iterator.next();
            for (int i = 0; i < row.length; i++) {
                boolean numeric = NumberUtils.isCreatable(row[i]);
                if (numeric){
                    numericOperation(Double.parseDouble(row[i]),title[i],cols);
                }else{
                    stringOperation(row[i],title[i],factors);
                }
            }
        }
        getMeans(cols);
        getStds(cols);
        getNss(factors);
    }

    public static void main(String[] args) throws IOException, SQLException {
        CSVServiceImpl csvService = new CSVServiceImpl();
        CSVData csvData = csvService.readFileFromDb("testdata.csv");
        csvService.dataAnalysis(new InputStreamReader(new ByteArrayInputStream(csvData.getData())));

//        String path = "D:\\Documents\\WeChat Files\\wxid_87qi65ssv6i852\\FileStorage\\File\\2020-11\\Java测试题\\testdata.csv";
//        CSVReader reader = new CSVReader(
//                new InputStreamReader(new FileInputStream(path)));
//        Iterator<String[]> iterator = reader.iterator();
//        Map<String,List<Double>> cols = new HashMap<>();
//        Map<String, Set<String>> factors = new HashMap<>();
//        String[] title = iterator.next();
//        while (iterator.hasNext()){
//            String[] row = iterator.next();
//            for (int i = 0; i < row.length; i++) {
//                boolean numeric = NumberUtils.isCreatable(row[i]);
//                if (numeric){
//                    numericOperation(Double.parseDouble(row[i]),title[i],cols);
//                }else{
//                    stringOperation(row[i],title[i],factors);
//                }
//            }
//        }
//        getMeans(cols);
//        getStds(cols);
//        getNss(factors);
    }

    /**
     * 离群值逻辑
     * @param factors
     * @return
     */
    private static Map<String,Integer> getNss(Map<String, Set<String>> factors) {
        Map<String,Integer> map = new HashMap<>();
        Set<Map.Entry<String, Set<String>>> entries = factors.entrySet();
        for (Map.Entry<String, Set<String>> entry: entries
             ) {
            map.put(entry.getKey(), entry.getValue().size());
        }
        return map;
    }

    //TODO 标准差实现逻辑待完成
    private static Map<String,Double> getStds(Map<String, List<Double>> cols) {
        Map<String,Double> map = new HashMap<>();
        Set<Map.Entry<String, List<Double>>> entries = cols.entrySet();

        return map;
    }

    /**
     * 均值计算逻辑
     * @param cols
     * @return
     */
    private static Map<String,Double> getMeans(Map<String, List<Double>> cols) {
        Map<String,Double> map = new HashMap<>();
        Set<Map.Entry<String, List<Double>>> entries = cols.entrySet();
        for (Map.Entry<String, List<Double>> entry: entries) {
            List<Double> values = entry.getValue();
            double average = values.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
            map.put(entry.getKey(),average);
        }
        return map;
    }

    /**
     * csv表格中数值类型收集逻辑
     * @param value 单元格值
     * @param title 单元格列名
     * @param map 单元格值集合
     */
    private static void numericOperation(double value, String title, Map<String,List<Double>> map) {
        List<Double> list = map.get(title);
        if (list==null) {
            list = new ArrayList<>();
        }
        list.add(value);
        map.put(title,list);
    }

    /**
     * csv表格中字符串类型收集逻辑
     * @param value 单元格值
     * @param title 单元格列名
     * @param map 单元格因子集合
     */
    private static void stringOperation(String value, String title, Map<String, Set<String>> map) {
        Set<String> set = map.get(title);
        if (set == null){
            set = new HashSet<>();
        }
        set.add(value);
        map.put(title,set);
    }

}
