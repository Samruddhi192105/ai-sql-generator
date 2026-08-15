package com.example.aisqlgenerator.model;

import java.util.List;

public class DatabaseSchema {

    private List<TableInfo> tables;

    public DatabaseSchema(List<TableInfo> tables) {
        this.tables = tables;
    }

    public List<TableInfo> getTables() {
        return tables;
    }
}