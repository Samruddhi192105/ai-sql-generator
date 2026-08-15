package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.model.DatabaseQueryResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryExecutionService {

    private final JdbcTemplate jdbcTemplate;

    public QueryExecutionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DatabaseQueryResult execute(String sql) {

        List<Map<String, Object>> result =
                jdbcTemplate.queryForList(sql);

        List<String> columns = new ArrayList<>();

        if (!result.isEmpty()) {
            columns.addAll(result.get(0).keySet());
        }

        List<List<Object>> rows = new ArrayList<>();

        for (Map<String, Object> row : result) {

            List<Object> values = new ArrayList<>();

            for (String column : columns) {
                values.add(row.get(column));
            }

            rows.add(values);
        }

        return new DatabaseQueryResult(
                columns,
                rows
        );
    }
}