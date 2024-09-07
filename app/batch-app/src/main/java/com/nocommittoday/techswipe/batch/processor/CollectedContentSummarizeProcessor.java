package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;
import com.nocommittoday.techswipe.infrastructure.alert.AlertCommand;
import com.nocommittoday.techswipe.infrastructure.alert.AlertManager;
import com.nocommittoday.techswipe.infrastructure.openai.collection.SummarizationProcessor;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

public class CollectedContentSummarizeProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentSummarizeProcessor.class);

    private final SummarizationProcessor summarizationProcessor;
    private final AlertManager alertManager;

    public CollectedContentSummarizeProcessor(
            SummarizationProcessor summarizationProcessor,
            AlertManager alertManager
    ) {
        this.summarizationProcessor = summarizationProcessor;
        this.alertManager = alertManager;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContentEntityEditor editor = item.toEditor();
        CollectedContent collectedContent = item.toDomain();

        try {
            Summary summary = summarizationProcessor.summarize(collectedContent);
            CollectedContent summarized = collectedContent.summarize(summary);
            editor.setSummary(Objects.requireNonNull(summarized.getSummary()).getContent());
            editor.setStatus(summarized.getStatus());
        } catch (DomainValidationException e) {
            log.error("CollectedContent.id={} 의 요약에 실패했습니다.", item.getId(), e);
            CollectedContent summarizationFailed = collectedContent.failSummarization();
            editor.setStatus(summarizationFailed.getStatus());
            alertManager.alert(
                    AlertCommand.builder()
                            .title("CollectedContentSummarizeJob CollectedContent 요약 실��")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(e)
                            .build()
            );
        }

        item.edit(editor);
        return item;
    }
}
