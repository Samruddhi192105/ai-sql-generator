package com.example.aisqlgenerator.model;

import java.util.List;

public class DatabaseQueryResult {

    private final List<String> columns;
    private final List<List<Object>> rows;

    public DatabaseQueryResult(
            List<String> columns,
            List<List<Object>> rows) {

        this.columns = columns;
        this.rows = rows;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<Object>> getRows() {
        return rows;
    }
}