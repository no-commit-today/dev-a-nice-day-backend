package com.nocommittoday.client.openai.model;

import org.springframework.lang.Nullable;

public record Choice(
        int index,
        Message message,
        FinishReason finishReason,
        @Nullable LogProbs logprobs
) {
}
