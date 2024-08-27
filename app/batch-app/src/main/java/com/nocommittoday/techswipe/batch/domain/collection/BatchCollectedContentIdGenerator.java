package com.nocommittoday.techswipe.batch.domain.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.core.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class BatchCollectedContentIdGenerator {

    private final IdGenerator idGenerator;

    public BatchCollectedContentIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public CollectedContentId nextId() {
        return new CollectedContentId(idGenerator.nextId());
    }
}
