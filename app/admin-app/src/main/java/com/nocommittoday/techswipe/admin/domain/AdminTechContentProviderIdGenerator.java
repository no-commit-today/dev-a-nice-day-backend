package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class AdminTechContentProviderIdGenerator {

    private final IdGenerator idGenerator;

    public AdminTechContentProviderIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public TechContentProviderId nextId() {
        return new TechContentProviderId(idGenerator.nextId());
    }
}
