package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionIllegalProviderIdException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderExistsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentCollectService {

    private final CollectedContentIdGenerator idGenerator;

    private final TechContentProviderExistsReader techContentProviderExistsReader;

    private final CollectedContentAppender collectedContentAppender;

    public CollectedContent.Id collect(final ContentCollectCommand command) {
        if (!techContentProviderExistsReader.exists(command.providerId())) {
            throw new CollectionIllegalProviderIdException(command.providerId());
        }
        return collectedContentAppender.save(command.toDomain(idGenerator.nextId()));
    }
}
