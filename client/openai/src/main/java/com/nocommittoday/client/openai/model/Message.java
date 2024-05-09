package com.nocommittoday.client.openai.model;

import org.springframework.lang.Nullable;

public record Message(
        MessageRole role,
        @Nullable String content
) {

}
