package com.nocommittoday.techswipe.collection.adapter.out.mysql;

import com.nocommittoday.techswipe.collection.application.port.out.CollectedContentUpdatePort;
import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
class CollectedContentUpdateAdapter implements CollectedContentUpdatePort {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    @Override
    @Transactional
    public void update(final CollectedContent collectedContent) {
        final CollectedContentEntity entity = collectedContentJpaRepository.findById(collectedContent.getId().id())
                .orElseThrow(() -> new CollectionNotFoundException(collectedContent.getId()));
        entity.update(collectedContent);
    }
}
