package com.devniceday.batch.job;

import com.devniceday.batch.domain.image.ContentImageStore;
import com.devniceday.module.alert.AlertCommand;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.module.idgenerator.IdGenerator;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchTechContentEntity;
import org.springframework.batch.item.ItemProcessor;

public class CollectedContentPublishJobItemProcessor
        implements ItemProcessor<BatchCollectedContentEntity, BatchTechContentEntity> {

    private final ContentImageStore contentImageStore;
    private final IdGenerator idGenerator;
    private final AlertManager alertManager;

    public CollectedContentPublishJobItemProcessor(
            ContentImageStore contentImageStore,
            IdGenerator idGenerator,
            AlertManager alertManager
    ) {
        this.contentImageStore = contentImageStore;
        this.idGenerator = idGenerator;
        this.alertManager = alertManager;
    }

    @Override
    public BatchTechContentEntity process(BatchCollectedContentEntity item) throws Exception {
        Long imageId = null;
        try {
            if (item.getImageUrl() != null) {
                imageId = contentImageStore.store(item.getImageUrl(), "content");
            }
        } catch (Exception e) {
            alertManager.alert(
                    AlertCommand.builder()
                            .warn()
                            .title("CollectedContentPublishJob 이미지 저장 실패")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(e)
                            .build()
            );
        }

        item.published();

        return new BatchTechContentEntity(
                idGenerator.nextId(),
                item.getProviderId(),
                imageId,
                item.getUrl(),
                item.getTitle(),
                item.getSummary(),
                item.getPublishedDate()
        );
    }
}
