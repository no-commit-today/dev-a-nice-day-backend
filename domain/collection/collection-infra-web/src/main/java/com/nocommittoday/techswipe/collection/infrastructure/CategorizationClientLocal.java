package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

class CategorizationClientLocal implements CategorizationClient {

    private Random random = new Random();

    @Override
    public String categorize(CollectedContent collectedContent) {
        int numCategories = random.nextInt(
                CategorizationProcessor.MIN_CATEGORY_NUM, CategorizationProcessor.MAX_CATEGORY_NUM + 1);
        Set<CollectionCategory> categories = new HashSet<>();
        for (int i = 0; i < numCategories; i++) {
            CollectionCategory category = CollectionCategory
                    .values()[random.nextInt(CollectionCategory.values().length)];
            categories.add(category);
        }
        return categories.stream()
                .map(category -> "- " + category.name())
                .collect(Collectors.joining("\n"));
    }
}
