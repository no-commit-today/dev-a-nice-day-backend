package com.nocommittoday.techswipe.content.adapter.out.mysql;

import com.nocommittoday.techswipe.content.application.port.out.ProviderSave;
import com.nocommittoday.techswipe.content.application.port.out.ProviderSavePort;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ProviderSaveAdapter implements ProviderSavePort {

    private final TechContentProviderJpaRepository repository;

    @Override
    public TechContentProvider.TechContentProviderId save(final ProviderSave command) {
        return new TechContentProvider.TechContentProviderId(repository.save(new TechContentProviderEntity(
                null,
                command.type(),
                command.title(),
                command.url(),
                Optional.ofNullable(command.iconId()).map(ImageEntity::from).orElse(null)
        )).getId());
    }
}
