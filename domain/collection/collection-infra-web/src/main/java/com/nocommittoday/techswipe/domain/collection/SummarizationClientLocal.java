package com.nocommittoday.techswipe.domain.collection;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SummarizationClientLocal implements SummarizationClient{

    @Override
    public String summarize(CollectedContent collectedContent) {
        int numSummaryLine = 3;
        return IntStream.rangeClosed(1, numSummaryLine)
                .mapToObj(i -> "- 요약-" + i)
                .collect(Collectors.joining("\n"));

    }
}