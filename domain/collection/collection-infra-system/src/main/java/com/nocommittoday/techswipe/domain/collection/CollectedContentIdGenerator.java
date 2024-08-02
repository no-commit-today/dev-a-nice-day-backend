package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.core.infrastructure.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class CollectedContentIdGenerator {

    private final IdGenerator idGenerator;

    public CollectedContentIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public CollectedContentId nextId() {
        return new CollectedContentId(idGenerator.nextId());
    }
}
