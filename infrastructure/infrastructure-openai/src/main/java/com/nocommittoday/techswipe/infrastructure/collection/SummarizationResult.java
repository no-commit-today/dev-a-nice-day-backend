package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.content.Summary;

import javax.annotation.Nullable;

public record SummarizationResult(
        boolean success,
        @Nullable Summary summary,
        @Nullable Exception exception
) {
    public static SummarizationResult success(Summary summary) {
        return new SummarizationResult(true, summary, null);
    }

    public static SummarizationResult failure(Exception ex) {
        return new SummarizationResult(false, null, ex);
    }
}
