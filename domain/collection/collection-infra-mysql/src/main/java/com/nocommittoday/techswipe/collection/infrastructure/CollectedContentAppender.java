package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class CollectedContentAppender {

    private final CollectedContentJpaRepository repository;
    private final CollectedContentEntityMapper mapper;

    public CollectedContentAppender(CollectedContentJpaRepository repository, CollectedContentEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CollectedContentId save(ContentCollect contentCollect) {
        return new CollectedContentId(Objects.requireNonNull(
                repository.save(mapper.from(contentCollect)).getId()
        ));
    }
}
