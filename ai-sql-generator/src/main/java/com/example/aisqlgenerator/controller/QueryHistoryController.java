package com.example.aisqlgenerator.controller;

import com.example.aisqlgenerator.model.QueryHistory;
import com.example.aisqlgenerator.service.QueryHistoryService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query/history")
public class QueryHistoryController {

    private final QueryHistoryService historyService;

    public QueryHistoryController(
            QueryHistoryService historyService) {

        this.historyService = historyService;
    }


    // ============================================================
    // GET HISTORY
    // ============================================================

    @GetMapping
    public List<QueryHistory> getHistory(
            Authentication authentication) {

        Long userId =
                (Long) authentication.getPrincipal();

        return historyService.getHistory(userId);
    }


    // ============================================================
    // DELETE QUERY
    // ============================================================

    @DeleteMapping("/{id}")
    public void deleteQuery(
            @PathVariable Long id,
            Authentication authentication) {

        Long userId =
                (Long) authentication.getPrincipal();

        historyService.deleteQuery(
                id,
                userId
        );
    }
}