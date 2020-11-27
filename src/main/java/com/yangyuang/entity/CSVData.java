package com.yangyuang.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CSVData {

    @Id

    private String name;

    private byte[] data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
