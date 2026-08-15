package com.example.aisqlgenerator.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    private final JdbcTemplate jdbcTemplate;

    public TestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/api/test-db")
    public List<Map<String, Object>> testDatabase() {

        String sql = "SELECT * FROM employees";

        return jdbcTemplate.queryForList(sql);
    }
}