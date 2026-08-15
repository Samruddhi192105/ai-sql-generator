package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.model.QueryHistory;
import com.example.aisqlgenerator.repository.QueryHistoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryHistoryService {

    private final QueryHistoryRepository repository;

    public QueryHistoryService(
            QueryHistoryRepository repository) {

        this.repository = repository;
    }


    // ============================================================
    // SAVE
    // ============================================================

    public void save(
            Long userId,
            String naturalLanguageQuery,
            String generatedSql,
            String executionStatus) {

        repository.save(
                userId,
                naturalLanguageQuery,
                generatedSql,
                executionStatus
        );
    }


    // ============================================================
    // GET HISTORY
    // ============================================================

    public List<QueryHistory> getHistory(
            Long userId) {

        return repository.findByUserId(userId);
    }


    // ============================================================
    // DELETE QUERY
    // ============================================================

    public void deleteQuery(
            Long queryId,
            Long userId) {

        repository.deleteByIdAndUserId(
                queryId,
                userId
        );
    }
}