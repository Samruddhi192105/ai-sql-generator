package com.example.aisqlgenerator.model;

import java.util.List;

public record AmbiguityResponse(
        boolean ambiguous,
        String question,
        List<String> options
) {
}
