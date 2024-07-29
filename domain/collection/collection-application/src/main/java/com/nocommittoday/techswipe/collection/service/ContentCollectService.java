package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionAlreadyCollectedException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionIllegalProviderIdException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUrlExistsReader;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderExistsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentCollectService {

    private final CollectedContentIdGenerator idGenerator;

    private final TechContentProviderExistsReader techContentProviderExistsReader;

    private final CollectedContentUrlExistsReader collectedContentUrlExistsReader;

    private final CollectedContentAppender collectedContentAppender;

    public CollectedContentId collect(final ContentCollectCommand command) {
        if (!techContentProviderExistsReader.exists(command.providerId())) {
            throw new CollectionIllegalProviderIdException(command.providerId());
        }
        if (collectedContentUrlExistsReader.exists(command.url())) {
            throw new CollectionAlreadyCollectedException(command.url());
        }
        return collectedContentAppender.save(command.toDomain(idGenerator.nextId()));
    }
}
