package com.nocommittoday.techswipe.batch.processor;

import com.nocommittoday.techswipe.batch.application.PromptWithInMemoryCacheReader;
import com.nocommittoday.techswipe.batch.exception.CategorizeFailureException;
import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.collection.infrastructure.CollectionProcessor;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

@RequiredArgsConstructor
public class CollectedContentCategorizeProcessor implements ItemProcessor<CollectedContentEntity, CollectedContentEntity> {

    private final PromptWithInMemoryCacheReader promptReader;
    private final CollectionProcessor collectionProcessor;

    @Override
    public CollectedContentEntity process(final CollectedContentEntity item) throws Exception {
        final CollectedContent collectedContent = item.toDomain();
        final Prompt prompt = promptReader.get(PromptType.CATEGORIZE, item.getProvider().getType());
        final List<CollectionCategory> categories = collectionProcessor.categorize(prompt, collectedContent.getContent());
        if (categories.isEmpty()) {
            throw new CategorizeFailureException(collectedContent.getId(), prompt.getId());
        }
        final CollectedContent categorized = collectedContent.categorize(categories);
        return CollectedContentEntity.from(categorized);
    }
}
