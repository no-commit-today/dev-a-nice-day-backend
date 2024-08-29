package com.nocommittoday.techswipe.storage.mysql.batch;

import com.nocommittoday.techswipe.domain.content.TechContentWithProvider;
import com.nocommittoday.techswipe.domain.content.TechContentCreate;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BatchTechContentEntityMapper {

    private final TechContentJpaRepository techContentJpaRepository;
    private final BatchTechContentProviderEntityMapper techContentProviderEntityMapper;
    private final BatchImageEntityMapper imageEntityMapper;

    public BatchTechContentEntityMapper(
            TechContentJpaRepository techContentJpaRepository,
            BatchTechContentProviderEntityMapper techContentProviderEntityMapper,
            BatchImageEntityMapper imageEntityMapper
    ) {
        this.techContentJpaRepository = techContentJpaRepository;
        this.techContentProviderEntityMapper = techContentProviderEntityMapper;
        this.imageEntityMapper = imageEntityMapper;
    }

    public TechContentEntity from(TechContentId id) {
        return techContentJpaRepository.getReferenceById(id.value());
    }

    public TechContentEntity from(TechContentWithProvider domain) {
        TechContentEntity entity = new TechContentEntity(
                domain.getId().value(),
                techContentProviderEntityMapper.from(domain.getProvider().getId()),
                imageEntityMapper.from(domain.getImageId()),
                domain.getUrl(),
                domain.getTitle(),
                domain.getSummary().getContent(),
                domain.getPublishedDate()
        );
        domain.getCategories().forEach(entity::addCategory);
        return entity;
    }

    public TechContentEntity from(TechContentCreate techContentCreate) {
        TechContentEntity entity = new TechContentEntity(
                techContentCreate.id().value(),
                techContentProviderEntityMapper.from(techContentCreate.providerId()),
                imageEntityMapper.from(techContentCreate.imageId()),
                techContentCreate.url(),
                techContentCreate.title(),
                techContentCreate.summary(),
                techContentCreate.publishedDate()
        );
        techContentCreate.categories().forEach(entity::addCategory);
        return entity;
    }
}
