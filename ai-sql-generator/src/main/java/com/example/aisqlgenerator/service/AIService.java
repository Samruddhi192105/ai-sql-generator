package com.example.aisqlgenerator.service;

import com.example.aisqlgenerator.exception.AIException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AIService {

    private final RestClient restClient;

    public AIService() {

        String ollamaUrl = System.getenv()
        .getOrDefault(
                "OLLAMA_URL",
                "http://host.docker.internal:11434"
        );

        this.restClient = RestClient.builder()
                .baseUrl(ollamaUrl)
                .build();
    }

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
    // 4. OLLAMA REQUEST
    // ============================================================

    private record OllamaRequest(
            String model,
            String prompt,
            boolean stream
    ) {
    }

    // ============================================================
    // 5. OLLAMA RESPONSE
    // ============================================================

    private record OllamaResponse(
            String response,
            boolean done
    ) {
    }
}       