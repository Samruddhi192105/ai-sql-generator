package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.dto.QueryRequest;
import com.example.aisqlgenerator.model.DatabaseSchema;
import com.example.aisqlgenerator.model.QueryResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    private final SchemaService schemaService;
    private final AIService aiService;
    private final QueryHistoryService historyService;
    private final ValidationService validationService;

    public QueryService(
            SchemaService schemaService,
            AIService aiService,
            QueryHistoryService historyService,
            ValidationService validationService) {

        this.schemaService = schemaService;
        this.aiService = aiService;
        this.historyService = historyService;
        this.validationService = validationService;
    }

    public QueryResponse processQuery(
            QueryRequest request) {

        // --------------------------------------------------------
        // 1. Validate user question
        // --------------------------------------------------------

        if (request == null ||
                request.getQuestion() == null ||
                request.getQuestion().isBlank()) {

            throw new IllegalArgumentException(
                    "Question cannot be empty"
            );
        }

        // --------------------------------------------------------
        // 2. Generate SQL
        // --------------------------------------------------------

        String sql =
                aiService.generateSQL(
                        request.getQuestion()
                );

        // --------------------------------------------------------
        // 3. Validate SQL syntax
        // --------------------------------------------------------

        validationService.validate(sql);

        // --------------------------------------------------------
        // 4. Explain SQL
        // --------------------------------------------------------

        String explanation =
                aiService.explainSQL(sql);

        // --------------------------------------------------------
        // 5. Save generated query to history
        // --------------------------------------------------------

        Long userId =
                (Long) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        historyService.save(
                userId,
                request.getQuestion(),
                sql,
                "SUCCESS"
        );

        // --------------------------------------------------------
        // 6. Return SQL + explanation
        //
        // No database rows are returned.
        // --------------------------------------------------------

        return new QueryResponse(
                "success",
                sql,
                explanation,
                null,
                null,
                null,
                null
        );
    }
}