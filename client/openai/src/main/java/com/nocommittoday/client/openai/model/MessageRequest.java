package com.nocommittoday.client.openai.model;

public record MessageRequest(
        MessageRole role,
        String content
) {

}
