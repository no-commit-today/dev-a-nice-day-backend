package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

class CategorizationClientLocal implements CategorizationClient {

    private final Random random = new Random();

    @Override
    public String categorize(final CollectedContent collectedContent) {
        final int numCategories = random.nextInt(
                CollectionProcessor.MIN_CATEGORY_NUM, CollectionProcessor.MAX_CATEGORY_NUM + 1);
        final Set<CollectionCategory> categories = new HashSet<>();
        for (int i = 0; i < numCategories; i++) {
            final CollectionCategory category = CollectionCategory
                    .values()[random.nextInt(CollectionCategory.values().length)];
            categories.add(category);
        }
        return categories.stream()
                .map(category -> "- " + category.name())
                .collect(Collectors.joining("\n"));
    }
}
