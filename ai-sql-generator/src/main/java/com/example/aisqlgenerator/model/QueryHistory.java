package com.example.aisqlgenerator.model;

import java.time.LocalDateTime;

public class QueryHistory {

    private Long id;
    private Long userId;
    private String naturalLanguageQuery;
    private String generatedSql;
    private String executionStatus;
    private LocalDateTime createdAt;

    public QueryHistory(
            Long id,
            Long userId,
            String naturalLanguageQuery,
            String generatedSql,
            String executionStatus,
            LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.naturalLanguageQuery = naturalLanguageQuery;
        this.generatedSql = generatedSql;
        this.executionStatus = executionStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNaturalLanguageQuery() {
        return naturalLanguageQuery;
    }

    public String getGeneratedSql() {
        return generatedSql;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}