package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.core.infrastructure.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectedContentIdGenerator {

    private final IdGenerator idGenerator;

    public CollectedContent.Id nextId() {
        return new CollectedContent.Id(idGenerator.nextId());
    }
}
