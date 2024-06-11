package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.application.port.out.ProviderExistsReaderPort;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProviderExistsReaderAdapter implements ProviderExistsReaderPort {

    private final TechContentJpaRepository repository;

    @Override
    public boolean exists(final TechContentProvider.TechContentProviderId id) {
        return repository.existsById(id.value());
    }
}
