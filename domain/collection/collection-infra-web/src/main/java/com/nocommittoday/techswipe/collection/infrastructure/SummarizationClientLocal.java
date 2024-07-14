package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SummarizationClientLocal implements SummarizationClient{

    @Override
    public String summarize(final CollectedContent collectedContent) {
        final int numSummaryLine = 3;
        return IntStream.rangeClosed(1, numSummaryLine)
                .mapToObj(i -> "- 요약-" + i)
                .collect(Collectors.joining("\n"));

    }
}
