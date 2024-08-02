package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CollectedContentUpdater {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public CollectedContentUpdater(CollectedContentJpaRepository collectedContentJpaRepository) {
        this.collectedContentJpaRepository = collectedContentJpaRepository;
    }

    @Transactional
    public void update(CollectedContent collectedContent) {
        CollectedContentEntity entity = collectedContentJpaRepository.findById(collectedContent.getId().value())
                .orElseThrow(() -> new CollectionNotFoundException(collectedContent.getId()));
        entity.update(collectedContent);
    }
}
