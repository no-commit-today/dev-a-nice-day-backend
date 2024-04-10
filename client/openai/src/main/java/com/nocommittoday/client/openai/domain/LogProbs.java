package com.nocommittoday.client.openai.domain;

import java.util.List;

public record LogProbs(
        List<Content> content
) {

    public record Content(
            String token,
            double logprob,
            byte[] bytes,
            List<TopLogProb> topLogprobs
    ) {
    }

    public record TopLogProb(
            String token,
            double logprob,
            byte[] bytes
    ) {
    }
}
