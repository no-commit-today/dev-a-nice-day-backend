package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.core.infrastructure.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TechContentProviderIdGenerator {

    private final IdGenerator idGenerator;

    public TechContentProviderId nextId() {
        return new TechContentProviderId(idGenerator.nextId());
    }
}
