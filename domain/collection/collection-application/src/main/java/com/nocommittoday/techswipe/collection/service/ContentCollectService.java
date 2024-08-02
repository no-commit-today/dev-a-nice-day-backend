package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionAlreadyCollectedException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionIllegalProviderIdException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUrlExistsReader;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderExistsReader;
import org.springframework.stereotype.Service;

@Service
public class ContentCollectService {

    private final CollectedContentIdGenerator idGenerator;
    private final TechContentProviderExistsReader techContentProviderExistsReader;
    private final CollectedContentUrlExistsReader collectedContentUrlExistsReader;
    private final CollectedContentAppender collectedContentAppender;

    public ContentCollectService(
            CollectedContentIdGenerator idGenerator,
            TechContentProviderExistsReader techContentProviderExistsReader,
            CollectedContentUrlExistsReader collectedContentUrlExistsReader,
            CollectedContentAppender collectedContentAppender
    ) {
        this.idGenerator = idGenerator;
        this.techContentProviderExistsReader = techContentProviderExistsReader;
        this.collectedContentUrlExistsReader = collectedContentUrlExistsReader;
        this.collectedContentAppender = collectedContentAppender;
    }

    public CollectedContentId collect(ContentCollectCommand command) {
        if (!techContentProviderExistsReader.exists(command.providerId())) {
            throw new CollectionIllegalProviderIdException(command.providerId());
        }
        if (collectedContentUrlExistsReader.exists(command.url())) {
            throw new CollectionAlreadyCollectedException(command.url());
        }
        return collectedContentAppender.save(command.toDomain(idGenerator.nextId()));
    }
}
