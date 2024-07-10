package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.core.infrastructure.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TechContentProviderIdGenerator {

    private final IdGenerator idGenerator;

    public TechContentProvider.Id nextId() {
        return new TechContentProvider.Id(idGenerator.nextId());
    }
}
