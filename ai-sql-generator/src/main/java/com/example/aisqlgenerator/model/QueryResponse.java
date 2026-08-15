package com.example.aisqlgenerator.model;

import java.util.List;

public record QueryResponse(
        String status,
        String sql,
        String explanation,
        List<String> columns,
        List<List<Object>> rows,
        String question,
        List<String> options
) {
}