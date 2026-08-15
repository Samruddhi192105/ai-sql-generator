package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.model.ColumnInfo;
import com.example.aisqlgenerator.model.DatabaseSchema;
import com.example.aisqlgenerator.model.TableInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SchemaService {

    private final JdbcTemplate jdbcTemplate;

    public SchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DatabaseSchema getSchema() {

        String tableQuery = """
                SELECT table_name
                FROM information_schema.tables
                WHERE table_schema = 'public'
                AND table_type = 'BASE TABLE'
                ORDER BY table_name
                """;

        List<String> tableNames = jdbcTemplate.query(
                tableQuery,
                (rs, rowNum) -> rs.getString("table_name")
        );

        List<TableInfo> tables = new ArrayList<>();

        for (String tableName : tableNames) {

            String columnQuery = """
                    SELECT column_name, data_type
                    FROM information_schema.columns
                    WHERE table_schema = 'public'
                    AND table_name = ?
                    ORDER BY ordinal_position
                    """;

            List<ColumnInfo> columns = jdbcTemplate.query(
                    columnQuery,
                    (rs, rowNum) -> new ColumnInfo(
                            rs.getString("column_name"),
                            rs.getString("data_type")
                    ),
                    tableName
            );

            tables.add(new TableInfo(tableName, columns));
        }

        return new DatabaseSchema(tables);
    }
}