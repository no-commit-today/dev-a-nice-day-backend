package com.nocommittoday.techswipe.collection.application.service;

import com.nocommittoday.techswipe.collection.application.port.in.CollectionCategorizeUseCase;
import com.nocommittoday.techswipe.collection.application.port.out.CategorizePort;
import com.nocommittoday.techswipe.collection.application.port.out.CollectedContentReaderPort;
import com.nocommittoday.techswipe.collection.application.port.out.CollectedContentUpdatePort;
import com.nocommittoday.techswipe.collection.application.port.out.PromptReaderPort;
import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.application.port.out.ProviderReaderPort;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class CollectionCategorizeService implements CollectionCategorizeUseCase {

    private final CollectedContentReaderPort collectedContentReaderPort;
    private final ProviderReaderPort providerReaderPort;
    private final PromptReaderPort promptReaderPort;
    private final CategorizePort categorizePort;
    private final CollectedContentUpdatePort collectedContentUpdatePort;


    // FIXME 동시성 문제 발생할 수 있음 -> 아직 동시에 호출될 일이 없으므로 보류
    @Override
    public void categorize(final CollectedContent.CollectedContentId id) {
        final CollectedContent collectedContent = collectedContentReaderPort.get(id);
        final TechContentProvider contentProvider = providerReaderPort.get(collectedContent.getProviderId());
        final Prompt prompt = promptReaderPort.get(PromptType.CATEGORIZE, contentProvider.getType());
        final List<CollectionCategory> categories = categorizePort.categorize(
                prompt.getContent(), collectedContent.getContent());

        final CollectedContent categorized = collectedContent.categorize(categories);
        collectedContentUpdatePort.update(categorized);
    }
}
