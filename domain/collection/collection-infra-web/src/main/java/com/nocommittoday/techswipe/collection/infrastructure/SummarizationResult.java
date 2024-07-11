package com.nocommittoday.techswipe.collection.infrastructure;

import javax.annotation.Nullable;

public record SummarizationResult(
        boolean success,
        @Nullable String summary,
        @Nullable Exception exception
) {
    public static SummarizationResult success(final String summary) {
        return new SummarizationResult(true, summary, null);
    }

    public static SummarizationResult failure(final Exception ex) {
        return new SummarizationResult(false, null, ex);
    }
}
