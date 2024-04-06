package com.nocommittoday.client.openai.domain;

import org.springframework.lang.Nullable;

public record Message(
        MessageRole role,
        @Nullable String content
) {

}
