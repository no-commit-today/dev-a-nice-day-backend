package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.admin.domain.exception.AdminCollectionAlreadyCollectedException;
import com.nocommittoday.techswipe.admin.domain.exception.AdminCollectionCollectFailureException;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.domain.collection.ContentCollect;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminCollectedContentEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.admin.AdminTechContentProviderEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class AdminContentCollectService {

    private final CollectedContentIdGenerator collectedContentIdGenerator;
    private final AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository;
    private final AdminCollectedContentEntityJpaRepository adminCollectedContentEntityJpaRepository;

    public AdminContentCollectService(
            CollectedContentIdGenerator collectedContentIdGenerator,
            AdminTechContentProviderEntityJpaRepository adminTechContentProviderEntityJpaRepository,
            AdminCollectedContentEntityJpaRepository adminCollectedContentEntityJpaRepository
    ) {
        this.collectedContentIdGenerator = collectedContentIdGenerator;
        this.adminTechContentProviderEntityJpaRepository = adminTechContentProviderEntityJpaRepository;
        this.adminCollectedContentEntityJpaRepository = adminCollectedContentEntityJpaRepository;
    }

    public CollectedContentId collect(AdminContentCollectCommand command) {
        TechContentProviderEntity providerEntity = adminTechContentProviderEntityJpaRepository
                .findById(command.providerId().value())
                .filter(TechContentProviderEntity::isUsed)
                .orElseThrow(() -> new AdminCollectionCollectFailureException(command.providerId()));

        if (adminCollectedContentEntityJpaRepository.existsByUrl(command.url())) {
            throw new AdminCollectionAlreadyCollectedException(URI.create(command.url()));
        }

        CollectedContentId id = collectedContentIdGenerator.nextId();
        CollectedContent collectedContent = CollectedContent.collect(new ContentCollect(
                id,
                command.providerId(),
                command.url(),
                command.title(),
                command.publishedDate(),
                command.content(),
                command.imageUrl()
        ));

        adminCollectedContentEntityJpaRepository.save(new CollectedContentEntity(
                collectedContent.getId().value(),
                collectedContent.getStatus(),
                providerEntity,
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                collectedContent.getContent(),
                collectedContent.getImageUrl(),
                null,
                null
        ));

        return id;
    }
}
