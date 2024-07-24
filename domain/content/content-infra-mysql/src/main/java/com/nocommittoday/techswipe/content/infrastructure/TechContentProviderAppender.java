package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TechContentProviderAppender {

    private final TechContentProviderJpaRepository repository;

    public TechContentProviderId save(final TechContentProviderCreate command) {
        return new TechContentProviderId(Objects.requireNonNull(repository.save(
                new TechContentProviderEntity(
                        command.id().value(),
                        command.type(),
                        command.title(),
                        command.url(),
                        Optional.ofNullable(command.iconId()).map(ImageEntity::from).orElse(null)
                )
        ).getId()));
    }
}
