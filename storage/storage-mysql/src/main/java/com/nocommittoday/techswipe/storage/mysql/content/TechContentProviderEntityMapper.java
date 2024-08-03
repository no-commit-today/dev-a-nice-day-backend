package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechContentProvider;
import com.nocommittoday.techswipe.domain.content.TechContentProviderCreate;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TechContentProviderEntityMapper {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;
    private final ImageEntityMapper imageEntityMapper;

    public TechContentProviderEntityMapper(
            TechContentProviderJpaRepository techContentProviderJpaRepository,
            ImageEntityMapper imageEntityMapper
    ) {
        this.techContentProviderJpaRepository = techContentProviderJpaRepository;
        this.imageEntityMapper = imageEntityMapper;
    }

    public TechContentProviderEntity from(TechContentProviderId id) {
        return techContentProviderJpaRepository.getReferenceById(id.value());
    }

    public TechContentProviderEntity from(TechContentProvider domain) {
        return new TechContentProviderEntity(
                domain.getId().value(),
                domain.getType(),
                domain.getTitle(),
                domain.getUrl(),
                imageEntityMapper.from(domain.getIconId())
        );
    }

    public TechContentProviderEntity from(TechContentProviderCreate command) {
        return new TechContentProviderEntity(
                command.id().value(),
                command.type(),
                command.title(),
                command.url(),
                imageEntityMapper.from(command.iconId())
        );
    }
}
