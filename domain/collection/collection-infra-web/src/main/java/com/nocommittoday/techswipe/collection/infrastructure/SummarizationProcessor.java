package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SummarizationProcessor {

    private final SummarizationClient summarizationClient;

    private final SummarizationValidator summarizationValidator;

    public SummarizationResult summarize(final CollectedContent collectedContent) {
        try {
            final String responseContent = summarizationClient.summarize(collectedContent);

            if (!summarizationValidator.check(responseContent)) {
                throw new SummarizationResponseInvalidException(
                        "요약 결과가 올바르지 않습니다. ", collectedContent.getId(), responseContent
                );
            }

            return SummarizationResult.success(responseContent);
        } catch (final Exception ex) {
            return SummarizationResult.failure(ex);
        }
    }
}
