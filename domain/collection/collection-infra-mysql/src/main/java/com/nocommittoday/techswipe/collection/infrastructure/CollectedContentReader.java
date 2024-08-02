package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CollectedContentReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public CollectedContentReader(CollectedContentJpaRepository collectedContentJpaRepository) {
        this.collectedContentJpaRepository = collectedContentJpaRepository;
    }

    public CollectedContent get(CollectedContentId id) {
        return collectedContentJpaRepository.findById(id.value())
                .filter(CollectedContentEntity::isUsed)
                .map(CollectedContentEntity::toDomain)
                .orElseThrow(() -> new CollectionNotFoundException(id));
    }
}
