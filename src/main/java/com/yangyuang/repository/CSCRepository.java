package com.yangyuang.repository;

import com.yangyuang.entity.CSVData;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSCRepository extends JpaRepository<CSVData,String> {



}
