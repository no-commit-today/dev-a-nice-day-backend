package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectedContentEntityMapper {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    public CollectedContentEntity from(final ContentCollect contentCollect) {
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

    public CollectedContentEntity from(final CollectedContent collectedContent) {
        return new CollectedContentEntity(
                collectedContent.getId().value(),
                collectedContent.getStatus(),
                techContentProviderJpaRepository.getReferenceById(collectedContent.getProviderId().value()),
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                collectedContent.getContent(),
                collectedContent.getImageUrl(),
                collectedContent.getCategories(),
                collectedContent.getSummary()
        );
    }
}
