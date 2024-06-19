package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.application.PromptWithInMemoryCacheReader;
import com.nocommittoday.techswipe.batch.exception.CategorizeFailureException;
import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.PromptType;
import com.nocommittoday.techswipe.collection.infrastructure.CategorizationResult;
import com.nocommittoday.techswipe.collection.infrastructure.CollectionProcessor;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

@RequiredArgsConstructor
public class CollectedContentCategorizeProcessor implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final PromptWithInMemoryCacheReader promptReader;
    private final CollectionProcessor collectionProcessor;

    @Override
    public CollectedContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        final Prompt prompt = promptReader.get(PromptType.CATEGORIZE, item.getProvider().getType());
        final CategorizationResult categorizationResult = collectionProcessor.categorize(
                prompt, collectedContent.getContent());
        if (!categorizationResult.success()) {
            throw new CategorizeFailureException(collectedContent.getId(), prompt.getId());
        }
        final CollectedContent categorized = collectedContent.categorize(categorizationResult.categories());
        return CollectedContentEntity.from(categorized);
    }
}
