package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SummarizationProcessor {

    private static final Pattern SUMMARIZATION_RESULT_PATTERN = Pattern.compile("""
            ^1\\. [^\\n]+
            2\\. [^\\n]+
            3\\. [^\\n]+$""");

    private static final Pattern KOREAN_CONTAINED_PATTERN = Pattern.compile("[가-힣]");

    private final SummarizationClient summarizationClient;

    public SummarizationResult summarize(final CollectedContent collectedContent) {
        try {
            final String responseContent = summarizationClient.summarize(collectedContent);

            if (!SUMMARIZATION_RESULT_PATTERN.matcher(responseContent).matches()) {
                throw new SummarizationResponseInvalidException(
                        "요약 결과가 올바르지 않습니다. ", collectedContent.getId(), responseContent
                );
            }
            if (!KOREAN_CONTAINED_PATTERN.matcher(responseContent).find()) {
                throw new SummarizationResponseInvalidException(
                        "한글 요약 결과가 아닙니다. ", collectedContent.getId(), responseContent
                );
            }

            return SummarizationResult.success(responseContent);
        } catch (final Exception ex) {
            return SummarizationResult.failure(ex);
        }
    }
}
