package com.nocommittoday.client.openai.domain;

public record Usage(
        int promptTokens,
        int completionTokens,
        int totalTokens
) {
}
