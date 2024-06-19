package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CollectedContentReader {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    public CollectedContent get(final CollectedContent.CollectedContentId id) {
        return collectedContentJpaRepository.findById(id.id())
                .map(CollectedContentEntity::toDomain)
                .orElseThrow(() -> new CollectionNotFoundException(id));
    }
}
