package com.nocommittoday.techswipe.domain.collection;

import org.springframework.stereotype.Component;

@Component
public class SummarizationProcessor {

    private final SummarizationClient summarizationClient;
    private final SummarizationValidator summarizationValidator;

    public SummarizationProcessor(
            SummarizationClient summarizationClient,
            SummarizationValidator summarizationValidator
    ) {
        this.summarizationClient = summarizationClient;
        this.summarizationValidator = summarizationValidator;
    }

    public SummarizationResult summarize(CollectedContent collectedContent) {
        try {
            String responseContent = summarizationClient.summarize(collectedContent);

            if (!summarizationValidator.check(responseContent)) {
                throw new SummarizationResponseInvalidException(
                        "요약 결과가 올바르지 않습니다. ", collectedContent.getId(), responseContent
                );
            }

            return SummarizationResult.success(responseContent);
        } catch (Exception ex) {
            return SummarizationResult.failure(ex);
        }
    }
}
