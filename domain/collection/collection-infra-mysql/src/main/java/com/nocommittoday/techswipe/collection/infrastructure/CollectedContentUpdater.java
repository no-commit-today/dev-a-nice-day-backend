package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CollectedContentUpdater {

    private final CollectedContentJpaRepository collectedContentJpaRepository;

    @Transactional
    public void update(final CollectedContent collectedContent) {
        final CollectedContentEntity entity = collectedContentJpaRepository.findById(collectedContent.getId().value())
                .orElseThrow(() -> new CollectionNotFoundException(collectedContent.getId()));
        entity.update(collectedContent);
    }
}
