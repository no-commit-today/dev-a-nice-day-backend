package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProviderAppender {

    private final TechContentProviderJpaRepository repository;

    public TechContentProvider.Id save(final TechContentProviderCreate command) {
        return new TechContentProvider.Id(repository.save(new TechContentProviderEntity(
                null,
                command.type(),
                command.title(),
                command.url(),
                Optional.ofNullable(command.iconId()).map(ImageEntity::from).orElse(null)
        )).getId());
    }
}
