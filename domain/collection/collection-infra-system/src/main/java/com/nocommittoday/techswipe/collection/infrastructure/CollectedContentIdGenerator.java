package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.core.infrastructure.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectedContentIdGenerator {

    private final IdGenerator idGenerator;

    public CollectedContentId nextId() {
        return new CollectedContentId(idGenerator.nextId());
    }
}
