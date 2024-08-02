package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.domain.content.TechContentCreate;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntityMapper;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageStoreService;
import org.javatuples.Pair;
import org.springframework.batch.item.ItemProcessor;

import java.util.Objects;
import java.util.Optional;

public class CollectedContentPublishProcessor
        implements ItemProcessor<CollectedContentEntity, Pair<CollectedContentEntity, TechContentEntity>> {

    private final ImageStoreService imageStoreService;
    private final CollectedContentEntityMapper collectedContentEntityMapper;
    private final TechContentEntityMapper techContentEntityMapper;

    public CollectedContentPublishProcessor(
            ImageStoreService imageStoreService,
            CollectedContentEntityMapper collectedContentEntityMapper,
            TechContentEntityMapper techContentEntityMapper
    ) {
        this.imageStoreService = imageStoreService;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
        this.techContentEntityMapper = techContentEntityMapper;
    }

    @Override
    public Pair<CollectedContentEntity, TechContentEntity> process(CollectedContentEntity item) throws Exception {
        CollectedContent collectedContent = item.toDomain();
        if (!collectedContent.getStatus().publishable()) {
            throw new CollectionPublishUnableException(collectedContent.getId(), collectedContent.getStatus());
        }
        ImageId imageId = Optional.ofNullable(collectedContent.getImageUrl())
                .map(imageUrl -> imageStoreService.store(collectedContent.getImageUrl(), "content").get())
                .orElse(null);
        TechContentCreate content = new TechContentCreate(
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
        return Pair.with(
                collectedContentEntityMapper.from(collectedContent.published()),
                techContentEntityMapper.from(content)
        );
    }
}
