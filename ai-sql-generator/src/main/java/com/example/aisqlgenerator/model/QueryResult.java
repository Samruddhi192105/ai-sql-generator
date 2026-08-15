package com.example.aisqlgenerator.model;

import java.util.List;

public class QueryResult {

    private String sql;
    private String explanation;
    private List<String> columns;
    private List<List<Object>> rows;

    public QueryResult(
            String sql,
            String explanation,
            List<String> columns,
            List<List<Object>> rows) {

        this.sql = sql;
        this.explanation = explanation;
        this.columns = columns;
        this.rows = rows;
    }

    public String getSql() {
        return sql;
    }

    public String getExplanation() {
        return explanation;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<Object>> getRows() {
        return rows;
    }
}