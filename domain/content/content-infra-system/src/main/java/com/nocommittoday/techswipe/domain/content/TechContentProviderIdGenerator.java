package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.IdGenerator;
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
