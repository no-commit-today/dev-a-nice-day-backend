package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TechContentProviderUpdater {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    @Transactional
    public void update(final TechContentProvider provider) {
        final TechContentProviderEntity entity = techContentProviderJpaRepository.findById(provider.getId().value())
                .orElseThrow(() -> new TechContentProviderNotFoundException(provider.getId()));
        entity.update(provider);
    }
}
