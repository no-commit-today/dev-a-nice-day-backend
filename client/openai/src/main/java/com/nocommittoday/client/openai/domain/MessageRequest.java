package com.nocommittoday.client.openai.domain;

public record MessageRequest(
        MessageRole role,
        String content
) {

}
