package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
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
