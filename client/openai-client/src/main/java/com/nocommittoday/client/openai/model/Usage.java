package com.nocommittoday.client.openai.model;

public record Usage(
        int promptTokens,
        int completionTokens,
        int totalTokens
) {
}
