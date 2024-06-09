package com.nocommittoday.techswipe.collection.application.port.out;

import javax.annotation.Nullable;

public record SummarizationResult(
        boolean success,
        @Nullable String summary
) {
    public static SummarizationResult success(final String summary) {
        return new SummarizationResult(true, summary);
    }

    public static SummarizationResult failure() {
        return new SummarizationResult(false, null);
    }
}
