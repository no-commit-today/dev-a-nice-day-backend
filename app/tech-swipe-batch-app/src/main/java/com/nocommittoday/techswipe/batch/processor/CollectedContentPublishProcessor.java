package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.CollectionPublishUnableException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.domain.TechContentCreate;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

@RequiredArgsConstructor
public class CollectedContentPublishProcessor
        implements ItemProcessor<CollectedContentEntity, TechContentEntity> {

    private final ImageStoreService imageStoreService;

    @Override
    public TechContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        if (collectedContent.getStatus() != CollectionStatus.SUMMARIZED) {
            throw new CollectionPublishUnableException(collectedContent.getId(), collectedContent.getStatus());
        }
        final Image.ImageId imageId = imageStoreService.store(collectedContent.getImageUrl(), "content");
        final TechContentCreate content = new TechContentCreate(
                collectedContent.getProviderId(),
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                imageId,
                collectedContent.getSummary(),
                Objects.requireNonNull(collectedContent.getCategories()).stream()
                        .map(CollectionCategory::getTechCategory)
                        .toList()
        );
        return TechContentEntity.from(content);
    }
}
