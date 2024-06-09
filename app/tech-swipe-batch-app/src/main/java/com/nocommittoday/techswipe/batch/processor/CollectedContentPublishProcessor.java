package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.domain.vo.TechContentCreate;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.image.application.port.in.ImageStoreUseCase;
import com.nocommittoday.techswipe.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;

@RequiredArgsConstructor
public class CollectedContentPublishProcessor
        implements ItemProcessor<CollectedContentEntity, TechContentEntity> {

    private final ImageStoreUseCase imageStoreUseCase;

    @Override
    public TechContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        if (collectedContent.getStatus() != CollectionStatus.SUMMARIZED) {
            throw new CollectionPublishUnableException(collectedContent.getId(), collectedContent.getStatus());
        }
        final Image.ImageId imageId = imageStoreUseCase.store(collectedContent.getImageUrl(), "content");
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
