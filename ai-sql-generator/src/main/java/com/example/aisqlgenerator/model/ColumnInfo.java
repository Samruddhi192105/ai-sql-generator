package com.example.aisqlgenerator.model;

public class ColumnInfo {

    private String name;
    private String dataType;

    public ColumnInfo(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }
}