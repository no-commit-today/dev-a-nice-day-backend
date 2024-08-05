package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
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
