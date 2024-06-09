package com.nocommittoday.techswipe.content.adapter.out.mysql;

import com.nocommittoday.techswipe.content.application.port.out.ProviderReaderPort;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.ContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProviderReaderAdapter implements ProviderReaderPort {

    private final TechContentProviderJpaRepository providerJpaRepository;

    @Override
    public TechContentProvider get(final TechContentProvider.TechContentProviderId id) {
        return providerJpaRepository.findById(id.value())
                .map(TechContentProviderEntity::toDomain)
                .orElseThrow(() -> new ContentProviderNotFoundException(id));
    }
}
