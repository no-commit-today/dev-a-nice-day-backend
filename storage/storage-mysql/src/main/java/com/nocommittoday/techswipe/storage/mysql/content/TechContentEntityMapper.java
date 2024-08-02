package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechContent;
import com.nocommittoday.techswipe.domain.content.TechContentCreate;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TechContentEntityMapper {

    private final TechContentJpaRepository techContentJpaRepository;
    private final TechContentProviderEntityMapper techContentProviderEntityMapper;
    private final ImageEntityMapper imageEntityMapper;

    public TechContentEntityMapper(
            TechContentJpaRepository techContentJpaRepository,
            TechContentProviderEntityMapper techContentProviderEntityMapper,
            ImageEntityMapper imageEntityMapper
    ) {
        this.techContentJpaRepository = techContentJpaRepository;
        this.techContentProviderEntityMapper = techContentProviderEntityMapper;
        this.imageEntityMapper = imageEntityMapper;
    }

    public TechContentEntity from(TechContentId id) {
        return techContentJpaRepository.getReferenceById(id.value());
    }

    public TechContentEntity from(TechContent domain) {
        TechContentEntity entity = new TechContentEntity(
                domain.getId().value(),
                techContentProviderEntityMapper.from(domain.getProvider().getId()),
                imageEntityMapper.from(domain.getImageId()),
                domain.getUrl(),
                domain.getTitle(),
                domain.getSummary(),
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
