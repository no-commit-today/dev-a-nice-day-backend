package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.core.infrastructure.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class TechContentProviderIdGenerator {

    private final IdGenerator idGenerator;

    public TechContentProviderIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public TechContentProviderId nextId() {
        return new TechContentProviderId(idGenerator.nextId());
    }
}
