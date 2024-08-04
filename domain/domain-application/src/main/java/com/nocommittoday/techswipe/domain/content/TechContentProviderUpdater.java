package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TechContentProviderUpdater {

    private final TechContentProviderJpaRepository techContentProviderJpaRepository;
    private final TechContentProviderEntityMapper mapper;

    public TechContentProviderUpdater(
            TechContentProviderJpaRepository techContentProviderJpaRepository, TechContentProviderEntityMapper mapper
    ) {
        this.techContentProviderJpaRepository = techContentProviderJpaRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void update(TechContentProvider provider) {
        if (!techContentProviderJpaRepository.existsById(provider.getId().value())) {
            throw new TechContentProviderNotFoundException(provider.getId());
        }
        techContentProviderJpaRepository.save(mapper.from(provider));
    }
}
