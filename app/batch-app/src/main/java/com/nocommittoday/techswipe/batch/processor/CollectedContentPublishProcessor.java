package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.content.domain.TechContentCreate;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CollectedContentPublishProcessor
        implements ItemProcessor<CollectedContentEntity, Pair<CollectedContentEntity, TechContentEntity>> {

    private final ImageStoreService imageStoreService;

    private final CollectedContentEntityMapper collectedContentEntityMapper;

    @Override
    public Pair<CollectedContentEntity, TechContentEntity> process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        if (!collectedContent.getStatus().publishable()) {
            throw new CollectionPublishUnableException(collectedContent.getId(), collectedContent.getStatus());
        }
        final ImageId imageId = Optional.ofNullable(collectedContent.getImageUrl())
                .map(imageUrl -> imageStoreService.store(collectedContent.getImageUrl(), "content").get())
                .orElse(null);
        final TechContentCreate content = new TechContentCreate(
                collectedContent.getId().toTechContentId(),
                collectedContent.getProviderId(),
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                imageId,
                Objects.requireNonNull(collectedContent.getSummary()),
                Objects.requireNonNull(collectedContent.getCategories()).stream()
                        .map(CollectionCategory::getTechCategory)
                        .toList()
        );
        return Pair.with(collectedContentEntityMapper.from(collectedContent.published()), TechContentEntity.from(content));
    }
}
