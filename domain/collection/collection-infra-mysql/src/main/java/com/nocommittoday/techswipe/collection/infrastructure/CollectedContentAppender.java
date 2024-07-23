package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CollectedContentAppender {

    private final CollectedContentJpaRepository repository;

    public CollectedContent.Id save(final ContentCollect contentCollect) {
        return new CollectedContent.Id(Objects.requireNonNull(
                repository.save(CollectedContentEntity.from(contentCollect)).getId()
        ));
    }
}
