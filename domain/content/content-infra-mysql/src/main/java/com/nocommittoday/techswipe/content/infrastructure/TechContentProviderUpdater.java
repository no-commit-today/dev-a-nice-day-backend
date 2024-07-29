package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntityMapper;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TechContentProviderUpdater {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;

    private final TechContentProviderEntityMapper mapper;

    @Transactional
    public void update(final TechContentProvider provider) {
        if (!techContentProviderJpaRepository.existsById(provider.getId().value())) {
            throw new TechContentProviderNotFoundException(provider.getId());
        }
        techContentProviderJpaRepository.save(mapper.from(provider));
    }
}
