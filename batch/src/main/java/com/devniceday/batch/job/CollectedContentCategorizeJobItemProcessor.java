package com.devniceday.batch.job;

import com.devniceday.batch.domain.CategorizationException;
import com.devniceday.batch.domain.CategorizationPrompt;
import com.devniceday.batch.domain.CollectionCategory;
import com.devniceday.batch.domain.ContentCategorizer;
import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class CollectedContentCategorizeJobItemProcessor
        implements ItemProcessor<BatchCollectedContentEntity, BatchCollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentCategorizeJobItemProcessor.class);

    private final ContentCategorizer contentCategorizer;
    private final AlertManager alertManager;

    public CollectedContentCategorizeJobItemProcessor(
            ContentCategorizer contentCategorizer, AlertManager alertManager) {
        this.contentCategorizer = contentCategorizer;
        this.alertManager = alertManager;
    }

    @Override
    public BatchCollectedContentEntity process(BatchCollectedContentEntity item) throws Exception {
        CategorizationPrompt prompt = CategorizationPrompt.of(item.getTitle(), item.getContent());
        try {
            List<CollectionCategory> categoryList = contentCategorizer.categorize(prompt);
            item.categorized(categoryList);
        } catch (CategorizationException | NonTransientAiException e) {
            // TODO NonTransientAiException 를 배치 쪽 코드에 노출하고 싶지 않음 (도메인에서만 쓰거나..)
            // 예외를 재정의 하거나 모델을 변경하거나, 토큰을 짜르거나 하는 방법 사용
            log.error("CollectedContent.id={} 의 분류에 실패했습니다.", item.getId(), e);
            item.categorizationFailed();
            alertManager.alert(
                    AlertCommand.builder()
                            .error()
                            .title("CollectedContentCategorizeJob CollectedContent 분류 실패")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(e)
                            .build()
            );
        }
        return item;
    }
}
