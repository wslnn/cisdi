package com.yangyuang.controller;

import com.yangyuang.entity.CSVData;
import com.yangyuang.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.sql.SQLException;

@Controller
public class AnalysisController {
    @Autowired
    private CSVService csvService;

    @RequestMapping({"/dataOperation"})
    public void dataOperation(String name) throws IOException, SQLException {
        CSVData csvData = csvService.readFileFromDb(name);
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(csvData.getData()));
        csvService.dataAnalysis(reader);

    }

    @RequestMapping("/storeOperation")
    public void storeOperation(String path) throws IOException, SQLException {
        File file = new File(path);
        csvService.storeFile2Db(file);
    }
}
