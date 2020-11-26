package com.yangyuang.service;

import com.yangyuang.service.impl.CSVServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import static org.junit.Assert.*;


@SpringBootTest
public class CSVServiceTest {

    static CSVService csvService;

    @BeforeClass
    public static void init(){
        csvService = new CSVServiceImpl();
    }

    @Test
    public void testStore() throws IOException, SQLException {
        String path = "pom.xml";
        assertTrue(csvService.storeFile2Db(new File(path)));
    }
}
