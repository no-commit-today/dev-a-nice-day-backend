package com.nocommittoday.techswipe.batch.domain.content;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.core.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class BatchTechContentIdGenerator {

    private final IdGenerator idGenerator;

    public BatchTechContentIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public TechContentId nextId() {
        return new TechContentId(idGenerator.nextId());
    }
}
