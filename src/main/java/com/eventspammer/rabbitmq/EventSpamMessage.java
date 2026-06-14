package com.eventspammer.rabbitmq;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

public record EventSpamMessage(
        Instant timestamp,
        int attemptNumber,
        String requestName,
        String method,
        String path,
        JsonNode requestBody,
        boolean success,
        Integer statusCode,
        Long durationMillis,
        String responseBody,
        String errorMessage
) {
}
