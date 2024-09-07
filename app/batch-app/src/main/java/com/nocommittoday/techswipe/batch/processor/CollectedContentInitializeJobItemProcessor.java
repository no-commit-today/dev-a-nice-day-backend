package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.domain.collection.BatchCollectedContentInitializeService;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.infrastructure.alert.AlertCommand;
import com.nocommittoday.techswipe.infrastructure.alert.AlertManager;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CollectedContentInitializeJobItemProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentInitializeJobItemProcessor.class);

    private final BatchCollectedContentInitializeService initializeService;
    private final AlertManager alertManager;

    public CollectedContentInitializeJobItemProcessor(
            BatchCollectedContentInitializeService initializeService,
            AlertManager alertManager
    ) {
        this.initializeService = initializeService;
        this.alertManager = alertManager;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContentEntityEditor editor = item.toEditor();
        CollectedContent collectedContent = item.toDomain();
        Subscription subscription = item.getSubscription().toDomain();

        try {
            CollectedContent initialized = initializeService.initialize(subscription, collectedContent);
            editor.setStatus(initialized.getStatus());
            editor.setTitle(initialized.getTitle());
            editor.setPublishedDate(initialized.getPublishedDate());
            editor.setContent(initialized.getContent());
            editor.setImageUrl(initialized.getImageUrl());
        } catch (Exception e) {
            CollectedContent contentInitializationFailed = collectedContent.initializationFailed();
            editor.setStatus(contentInitializationFailed.getStatus());
            log.error("CollectedContent.id={} 의 초기화에 실패했습니다.", item.getId(), e);
            alertManager.alert(
                    AlertCommand.builder()
                            .title("CollectedContentInitializeJob CollectedContent 초기화 실패")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(e)
                            .build()
            );
        }

        item.edit(editor);
        return item;
    }
}
