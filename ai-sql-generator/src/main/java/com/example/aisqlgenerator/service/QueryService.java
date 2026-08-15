package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.dto.QueryRequest;
import com.example.aisqlgenerator.model.DatabaseSchema;
import com.example.aisqlgenerator.model.QueryResponse;

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
        // 3. Generate SQL directly
        //
        // NO ambiguity detection.
        // NO clarification AI call.
        // --------------------------------------------------------

        String sql =
                aiService.generateSQL(
                        request.getQuestion()
                );


        // --------------------------------------------------------
        // 4. Validate SQL syntax
        // --------------------------------------------------------

        validationService.validate(sql);


        // --------------------------------------------------------
        // 5. Explain SQL
        // --------------------------------------------------------

        String explanation =
                aiService.explainSQL(sql);


        // --------------------------------------------------------
        // 6. Save generated query to history
        // --------------------------------------------------------

        /*
         * Keep your existing history functionality.
         *
         * The exact authenticated-user handling depends on
         * your existing security implementation.
         */

        /*
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
        */


        // --------------------------------------------------------
        // 7. Return SQL + explanation
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