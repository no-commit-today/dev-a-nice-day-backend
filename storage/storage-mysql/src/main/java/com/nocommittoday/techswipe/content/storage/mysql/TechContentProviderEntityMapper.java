package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TechContentProviderEntityMapper {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    private final ImageEntityMapper imageEntityMapper;

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
