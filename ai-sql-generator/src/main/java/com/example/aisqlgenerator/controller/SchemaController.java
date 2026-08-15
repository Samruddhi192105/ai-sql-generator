package com.example.aisqlgenerator.controller;

import com.example.aisqlgenerator.model.DatabaseSchema;
import com.example.aisqlgenerator.service.SchemaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schema")
public class SchemaController {

    private final SchemaService schemaService;

    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @GetMapping
    public DatabaseSchema getSchema() {
        return schemaService.getSchema();
    }
}
