package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
public class LocalCollectionProcessor implements CollectionProcessor {

    private final Random random = new Random();

    @Override
    public CategorizationResult categorize(final Prompt prompt, final String content) {
        final int numCategories = random.nextInt(1, 4);
        final Set<CollectionCategory> categories = new HashSet<>();
        for (int i = 0; i < numCategories; i++) {
            final CollectionCategory category = CollectionCategory
                    .values()[random.nextInt(CollectionCategory.values().length)];
            categories.add(category);
        }
        return CategorizationResult.success(categories.stream().toList());
    }

    @Override
    public SummarizationResult summarize(final Prompt prompt, final String content) {
        return SummarizationResult.success("summary");
    }
}
