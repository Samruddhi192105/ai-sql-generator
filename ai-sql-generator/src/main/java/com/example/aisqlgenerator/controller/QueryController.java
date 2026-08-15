package com.example.aisqlgenerator.controller;

import com.example.aisqlgenerator.dto.QueryRequest;
import com.example.aisqlgenerator.model.QueryResponse;
import com.example.aisqlgenerator.service.QueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/generate")
    public QueryResponse generateQuery(
            @RequestBody QueryRequest request) {

        return queryService.processQuery(request);
    }
}