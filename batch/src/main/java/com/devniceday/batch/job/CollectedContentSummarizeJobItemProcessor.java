package com.devniceday.batch.job;

import com.devniceday.batch.domain.ContentSummarizer;
import com.devniceday.batch.domain.SummarizationException;
import com.devniceday.batch.domain.SummarizationPrompt;
import com.devniceday.batch.domain.Summary;
import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CollectedContentSummarizeJobItemProcessor
        implements ItemProcessor<BatchCollectedContentEntity, BatchCollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentSummarizeJobItemProcessor.class);

    private final ContentSummarizer contentSummarizer;
    private final AlertManager alertManager;

    public CollectedContentSummarizeJobItemProcessor(
            ContentSummarizer contentSummarizer, AlertManager alertManager) {
        this.contentSummarizer = contentSummarizer;
        this.alertManager = alertManager;
    }

    @Override
    public BatchCollectedContentEntity process(BatchCollectedContentEntity item) throws Exception {
        var prompt = SummarizationPrompt.of(item.getTitle(), item.getContent());
        try {
            Summary summary = contentSummarizer.summarize(prompt);
            item.summarized(summary.content());
        } catch (SummarizationException e) {
            log.error("CollectedContent.id={} 의 요약에 실패했습니다.", item.getId(), e);
            item.summarizationFailed();
            alertManager.alert(
                    AlertCommand.builder()
                            .error()
                            .title("CollectedContent 요약 실패")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(e)
                            .build()
            );
        }
        return item;
    }
}
