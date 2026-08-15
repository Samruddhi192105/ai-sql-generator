package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.exception.AIException;
import com.example.aisqlgenerator.model.ColumnInfo;
import com.example.aisqlgenerator.model.DatabaseSchema;
import com.example.aisqlgenerator.model.TableInfo;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AIService {

    private final RestClient restClient;

    public AIService() {

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }


    // ============================================================
    // 1. GENERATE SQL
    // ============================================================

    // ============================================================
// 1. GENERATE SQL
// ============================================================

public String generateSQL(String question) {

    String prompt = buildPrompt(question);

    OllamaRequest request = new OllamaRequest(
            "qwen2.5-coder:1.5b",
            prompt,
            false
    );

    OllamaResponse response;

    try {

        response = restClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OllamaResponse.class);

    } catch (Exception e) {

        throw new AIException(
                "Ollama AI service is unavailable",
                e
        );
    }

    if (response == null || response.response() == null) {

        throw new AIException(
                "No response received from Ollama"
        );
    }

    System.out.println("========== AI OUTPUT ==========");
    System.out.println(response.response());
    System.out.println("================================");

    String sql = response.response()
            .trim()
            .replace("```sql", "")
            .replace("```SQL", "")
            .replace("```", "")
            .trim();

    return sql;
}


// ============================================================
// 2. BUILD SQL PROMPT
// ============================================================

private String buildPrompt(String question) {

    return """
            You are an expert PostgreSQL SQL generator.

            Convert the user's natural-language request into
            exactly ONE valid PostgreSQL SQL statement.

            USER REQUEST:

            %s

            IMPORTANT RULES:

            1. The USER REQUEST is the only source of truth.

            2. Preserve the meaning of the user's request exactly.

            3. Do not change the subject of the request.

            4. Do not automatically use an employees table.

            5. Do not automatically use an employee table.

            6. Do not use any real database schema.

            7. This SQL is an example/generated statement only.
               It will NOT be executed against a real database.

            8. If the user mentions students, use a students table.

            9. If the user mentions employees, use an employees table.

            10. If the user mentions customers, use a customers table.

            11. If the user mentions products, use a products table.

            12. If the user mentions boys or girls and no specific
                entity is given, use a reasonable generic table such
                as students.

            13. Never introduce an unrelated entity.

            14. Do not add conditions that the user did not request.

            15. Do not invent unrelated filters.

            16. Do not ask for clarification.

            17. Generate SQL immediately.

            18. You may generate:

                SELECT
                INSERT
                UPDATE
                DELETE
                CREATE
                ALTER
                DROP
                TRUNCATE
                WITH

            19. Generate exactly ONE SQL statement.

            20. Return ONLY SQL.

            21. Do not use Markdown.

            22. Do not use code fences.

            23. Do not explain the SQL.

            24. Do not write anything before the SQL.

            25. Do not write anything after the SQL.

            SQL:

            """.formatted(question);
}

    // ============================================================
    // 3. EXPLAIN SQL
    // ============================================================

    public String explainSQL(String sql) {

        String prompt = """
                You are a PostgreSQL SQL explanation assistant.

                Explain the following SQL statement in simple language.

                IMPORTANT RULES:

                1. Use only 1 or 2 sentences.
                2. Explain what the SQL statement does.
                3. Mention the table or tables used when applicable.
                4. Mention important filters, joins, grouping, sorting,
                   inserted values, updated values, or deleted data
                   when applicable.
                5. Do not generate SQL.
                6. Do not repeat the SQL statement.
                7. Do not use Markdown.
                8. Do not use headings.
                9. Do not use bullet points.
                10. Do not provide examples.
                11. Do not provide tables.
                12. Do not explain PostgreSQL syntax.

                SQL STATEMENT:

                """ + sql;

        OllamaRequest request = new OllamaRequest(
                "qwen2.5-coder:1.5b",
                prompt,
                false
        );

        OllamaResponse response;

        try {

            response = restClient.post()
                    .uri("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(OllamaResponse.class);

        } catch (Exception e) {

            throw new AIException(
                    "Ollama AI service is unavailable",
                    e
            );
        }

        if (response == null || response.response() == null) {

            throw new AIException(
                    "No explanation received from Ollama"
            );
        }

        return response.response().trim();
    }


    // ============================================================
    // 3. ANALYZE QUESTION FOR AMBIGUITY
    // ============================================================

//     public AmbiguityResponse analyzeQuestion(
//             String question,
//             DatabaseSchema schema) {

//         /*
//          * IMPORTANT:
//          *
//          * The ambiguity detector intentionally does NOT receive
//          * the database schema.
//          *
//          * Its only job is to determine whether the user's natural
//          * language request itself is unclear.
//          *
//          * This prevents the model from inventing unrelated
//          * interpretations based on schema information.
//          */

//         String prompt = """
//                 You are a strict ambiguity detector for an AI SQL generator.

//                 USER QUESTION:
//                 """ + question + """

//                 Your ONLY task is to decide whether clarification is
//                 absolutely necessary before generating SQL.

//                 IMPORTANT:

//                 Analyze ONLY the USER QUESTION above.

//                 Do NOT use any other topic, example, assumption,
//                 or imaginary user request.

//                 DEFAULT BEHAVIOR:

//                 Assume the user's request is clear.

//                 If the request can reasonably be converted into
//                 a SQL statement, return ambiguous=false.

//                 DO NOT ask for clarification because:

//                 - another interpretation is theoretically possible
//                 - more filters could be added
//                 - more columns could be selected
//                 - sorting was not specified
//                 - grouping was not specified
//                 - a department was not specified
//                 - a date was not specified
//                 - the user did not provide every possible detail
//                 - multiple SQL statements could technically be written
//                 - you can imagine additional requirements

//                 NEVER invent information that is not present
//                 in the USER QUESTION.

//                 NEVER introduce unrelated topics.

//                 NEVER introduce salary, employees, departments,
//                 students, heights, dates, or any other subject
//                 unless the USER QUESTION itself mentions them.

//                 A clarification is allowed ONLY when the user's
//                 wording contains a genuine missing requirement
//                 that prevents a reasonable SQL interpretation.

//                 If the question is reasonably understandable,
//                 ALWAYS return:

//                 {
//                   "ambiguous": false,
//                   "question": null,
//                   "options": []
//                 }

//                 If clarification is genuinely required, return:

//                 {
//                   "ambiguous": true,
//                   "question": "Short clarification question",
//                   "options": [
//                     "First reasonable interpretation",
//                     "Second reasonable interpretation"
//                   ]
//                 }

//                 IMPORTANT OUTPUT RULES:

//                 1. Output ONLY valid JSON.
//                 2. Do not output Markdown.
//                 3. Do not output code fences.
//                 4. Do not explain your decision.
//                 5. Do not generate SQL.
//                 6. Prefer ambiguous=false.
//                 7. Never invent ambiguity.
//                 8. Never invent topics.
//                 9. Never invent filters.
//                 10. Never invent tables.
//                 11. Never invent columns.
//                 12. Never invent values.
//                 13. If ambiguous=false, question MUST be null.
//                 14. If ambiguous=false, options MUST be [].
//                 15. If ambiguous=true, provide exactly two options.
//                 """;

//         OllamaRequest request = new OllamaRequest(
//                 "qwen2.5-coder:1.5b",
//                 prompt,
//                 false
//         );

//         OllamaResponse response;

//         try {

//             response = restClient.post()
//                     .uri("/api/generate")
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .body(request)
//                     .retrieve()
//                     .body(OllamaResponse.class);

//         } catch (Exception e) {

//             throw new AIException(
//                     "Ollama AI service is unavailable",
//                     e
//             );
//         }

//         if (response == null || response.response() == null) {

//             throw new AIException(
//                     "No ambiguity response received from Ollama"
//             );
//         }

//         String json = response.response()
//                 .trim()
//                 .replace("```json", "")
//                 .replace("```JSON", "")
//                 .replace("```", "")
//                 .trim();

//         System.out.println(
//                 "========== AMBIGUITY AI OUTPUT =========="
//         );

//         System.out.println(json);

//         System.out.println(
//                 "=========================================="
//         );

//         try {

//             return objectMapper.readValue(
//                     json,
//                     AmbiguityResponse.class
//             );

//         } catch (Exception e) {

//             throw new AIException(
//                     "Invalid ambiguity response from AI: " + json,
//                     e
//             );
//         }
//     }


    // ============================================================
    // 5. BUILD DATABASE SCHEMA TEXT
    // ============================================================

    private String buildSchemaText(
            DatabaseSchema schema) {

        StringBuilder schemaText =
                new StringBuilder();

        if (schema == null || schema.getTables() == null) {
            return "No database schema available.\n";
        }

        for (TableInfo table : schema.getTables()) {

            schemaText.append("Table: ")
                    .append(table.getTableName())
                    .append("\n");

            if (table.getColumns() != null) {

                for (ColumnInfo column : table.getColumns()) {

                    schemaText.append("- ")
                            .append(column.getName())
                            .append(" ")
                            .append(column.getDataType())
                            .append("\n");
                }
            }

            schemaText.append("\n");
        }

        return schemaText.toString();
    }


    // ============================================================
    // 6. OLLAMA REQUEST
    // ============================================================

    private record OllamaRequest(
            String model,
            String prompt,
            boolean stream
    ) {
    }


    // ============================================================
    // 7. OLLAMA RESPONSE
    // ============================================================

    private record OllamaResponse(
            String response,
            boolean done
    ) {
    }
}       