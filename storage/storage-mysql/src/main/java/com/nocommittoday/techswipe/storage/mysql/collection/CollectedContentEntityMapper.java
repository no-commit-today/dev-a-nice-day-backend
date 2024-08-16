package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.collection.ContentCollect;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CollectedContentEntityMapper {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    public CollectedContentEntityMapper(TechContentProviderJpaRepository techContentProviderJpaRepository) {
        this.techContentProviderJpaRepository = techContentProviderJpaRepository;
    }

    public CollectedContentEntity from(ContentCollect contentCollect) {
        return new CollectedContentEntity(
                contentCollect.id().value(),
                CollectionStatus.INIT,
                techContentProviderJpaRepository.getReferenceById(contentCollect.providerId().value()),
                contentCollect.url(),
                contentCollect.title(),
                contentCollect.publishedDate(),
                contentCollect.content(),
                contentCollect.imageUrl(),
                null,
                null
        );
    }

    public CollectedContentEntity from(CollectedContent collectedContent) {
        return new CollectedContentEntity(
                collectedContent.getId().value(),
                collectedContent.getStatus(),
                techContentProviderJpaRepository.getReferenceById(collectedContent.getProviderId().value()),
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                collectedContent.getContent(),
                collectedContent.getImageUrl(),
                collectedContent.getCategoryList() != null ? collectedContent.getCategoryList().getContent() : null,
                collectedContent.getSummary() != null ? collectedContent.getSummary().getContent() : null
        );
    }
}
