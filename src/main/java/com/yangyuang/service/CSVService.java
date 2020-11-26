package com.yangyuang.service;

import com.yangyuang.entity.CSVData;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;


public interface CSVService {
    public boolean storeFile2Db(File file) throws IOException, SQLException;
    public CSVData readFileFromDb(String fileName) throws IOException, SQLException;
    public void dataAnalysis(InputStreamReader inputStreamReader);
}
