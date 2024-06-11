package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.application.port.out.CollectedContentSavePort;
import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CollectedContentSaveAdapter implements CollectedContentSavePort {

    private final CollectedContentJpaRepository repository;

    @Override
    public void save(final CollectedContent collectedContent) {
        repository.save(CollectedContentEntity.from(collectedContent));
    }
}
