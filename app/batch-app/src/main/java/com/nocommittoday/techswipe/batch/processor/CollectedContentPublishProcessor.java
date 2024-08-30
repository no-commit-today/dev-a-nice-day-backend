package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.domain.content.BatchTechContentIdGenerator;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.content.TechContent;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.infrastructure.aws.image.ImageStore;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchImageEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import org.springframework.batch.item.ItemProcessor;

import java.util.Optional;

public class CollectedContentPublishProcessor
        implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final ImageStore imageStore;
    private final BatchTechContentIdGenerator techContentIdGenerator;
    private final BatchImageEntityMapper imageEntityMapper;

    public CollectedContentPublishProcessor(
            ImageStore imageStore,
            BatchTechContentIdGenerator techContentIdGenerator,
            BatchImageEntityMapper imageEntityMapper
    ) {
        this.imageStore = imageStore;
        this.techContentIdGenerator = techContentIdGenerator;
        this.imageEntityMapper = imageEntityMapper;
    }

    @Override
    public CollectedContentEntity process(CollectedContentEntity item) throws Exception {
        CollectedContent collectedContent = item.toDomain();
        ImageId imageId = Optional.ofNullable(collectedContent.getImageUrl())
                .map(imageUrl -> imageStore.store(collectedContent.getImageUrl(), "content").get())
                .orElse(null);
        TechContentId techContentId = techContentIdGenerator.nextId();
        CollectedContent collectedContentPublished = collectedContent.published(techContentId);
        TechContent techContent = collectedContentPublished.toTechContent(imageId);

        return new CollectedContentEntity(
                collectedContentPublished.getId().value(),
                collectedContentPublished.getStatus(),
                item.getProvider(),
                item.getSubscription(),
                new TechContentEntity(
                        techContent.getId().value(),
                        item.getProvider(),
                        imageEntityMapper.from(techContent.getImageId()),
                        techContent.getUrl(),
                        techContent.getTitle(),
                        techContent.getSummary().getContent(),
                        techContent.getPublishedDate()
                ),
                collectedContentPublished.getUrl(),
                collectedContentPublished.getTitle(),
                collectedContentPublished.getPublishedDate(),
                collectedContentPublished.getContent(),
                collectedContentPublished.getImageUrl(),
                collectedContentPublished.getCategoryList() != null
                        ? collectedContentPublished.getCategoryList().getContent() : null,
                collectedContentPublished.getSummary() != null
                        ? collectedContentPublished.getSummary().getContent() : null
        );
    }
}
