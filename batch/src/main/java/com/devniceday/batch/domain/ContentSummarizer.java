package com.devniceday.batch.domain;

import com.devniceday.batch.openai.SummarizationClient;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class ContentSummarizer {

    private final SummarizationClient summarizationClient;

    public ContentSummarizer(SummarizationClient summarizationClient) {
        this.summarizationClient = summarizationClient;
    }

    @Retryable(retryFor = SummarizationException.class)
    public Summary summarize(SummarizationPrompt prompt) {
        String responseContent = summarizationClient.summarize(prompt);
        Summary summary = new Summary(responseContent);
        if (!summary.isValid()) {
            throw new SummarizationException("요약에 실패했습니다.\n"
                    + "[prompt]\n" + prompt + "\n"
                    + "[responseContent]\n" + responseContent);
        }
        return summary;
    }
}
