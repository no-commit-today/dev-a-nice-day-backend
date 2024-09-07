package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.domain.content.BatchTechContentIdGenerator;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.content.TechContent;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.exception.ImageApplicationException;
import com.nocommittoday.techswipe.infrastructure.alert.AlertCommand;
import com.nocommittoday.techswipe.infrastructure.alert.AlertManager;
import com.nocommittoday.techswipe.infrastructure.aws.image.ImageStore;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchImageEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityEditor;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Optional;

public class CollectedContentPublishProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentPublishProcessor.class);

    private final ImageStore imageStore;
    private final BatchTechContentIdGenerator techContentIdGenerator;
    private final BatchImageEntityMapper imageEntityMapper;
    private final AlertManager alertManager;

    public CollectedContentPublishProcessor(
            ImageStore imageStore,
            BatchTechContentIdGenerator techContentIdGenerator,
            BatchImageEntityMapper imageEntityMapper,
            AlertManager alertManager
    ) {
        this.imageStore = imageStore;
        this.techContentIdGenerator = techContentIdGenerator;
        this.imageEntityMapper = imageEntityMapper;
        this.alertManager = alertManager;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContent collectedContent = item.toDomain();
        CollectedContentEntityEditor editor = item.toEditor();

        try {
            ImageId imageId = Optional.ofNullable(collectedContent.getImageUrl())
                    .map(imageUrl -> imageStore.store(collectedContent.getImageUrl(), "content").get())
                    .orElse(null);
            TechContentId techContentId = techContentIdGenerator.nextId();
            CollectedContent collectedContentPublished = collectedContent.published(techContentId);
            TechContent techContent = collectedContentPublished.toTechContent(imageId);

            editor.setStatus(collectedContentPublished.getStatus());
            editor.setPublishedContent(new TechContentEntity(
                    techContent.getId().value(),
                    item.getProvider(),
                    imageEntityMapper.from(techContent.getImageId()),
                    techContent.getUrl(),
                    techContent.getTitle(),
                    techContent.getSummary().getContent(),
                    techContent.getPublishedDate()
            ));
        } catch (ImageApplicationException exception) {
            alertManager.alert(
                    AlertCommand.builder()
                            .warn()
                            .title("CollectedContentPublishJob 이미지 저장 실패")
                            .content(String.format("- CollectedContent.id: %d", item.getId()))
                            .ex(exception)
                            .build()
            );
        }

        item.edit(editor);
        return item;
    }
}
