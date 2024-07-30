package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CollectedContentAppender {

    private final CollectedContentJpaRepository repository;

    private final CollectedContentEntityMapper mapper;

    public CollectedContentId save(ContentCollect contentCollect) {
        return new CollectedContentId(Objects.requireNonNull(
                repository.save(mapper.from(contentCollect)).getId()
        ));
    }
}
