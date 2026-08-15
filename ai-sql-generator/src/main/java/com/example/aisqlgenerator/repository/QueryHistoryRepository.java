package com.example.aisqlgenerator.repository;

import com.example.aisqlgenerator.model.QueryHistory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public QueryHistoryRepository(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }


    // ============================================================
    // SAVE
    // ============================================================

    public void save(
            Long userId,
            String naturalLanguageQuery,
            String generatedSql,
            String executionStatus) {

        String sql = """
                INSERT INTO query_history
                (
                    user_id,
                    natural_language_query,
                    generated_sql,
                    execution_status
                )
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                userId,
                naturalLanguageQuery,
                generatedSql,
                executionStatus
        );
    }


    // ============================================================
    // FIND HISTORY
    // ============================================================

    public List<QueryHistory> findByUserId(
            Long userId) {

        String sql = """
                SELECT
                    id,
                    user_id,
                    natural_language_query,
                    generated_sql,
                    execution_status,
                    created_at
                FROM query_history
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new QueryHistory(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("natural_language_query"),
                        rs.getString("generated_sql"),
                        rs.getString("execution_status"),
                        rs.getTimestamp("created_at")
                                .toLocalDateTime()
                ),
                userId
        );
    }


    // ============================================================
    // DELETE BY ID + USER ID
    // ============================================================

    public void deleteByIdAndUserId(
            Long queryId,
            Long userId) {

        String sql = """
                DELETE FROM query_history
                WHERE id = ?
                AND user_id = ?
                """;

        jdbcTemplate.update(
                sql,
                queryId,
                userId
        );
    }
}