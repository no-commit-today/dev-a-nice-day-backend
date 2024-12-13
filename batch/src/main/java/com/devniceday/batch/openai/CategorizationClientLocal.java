package com.devniceday.batch.openai;

import com.devniceday.batch.domain.CategorizationPrompt;
import com.devniceday.batch.domain.CollectionCategory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class CategorizationClientLocal implements CategorizationClient {

    @Override
    public String categorize(CategorizationPrompt prompt) {
        Random random = new Random();
        int numCategories = random.nextInt(
                CollectionCategory.MIN_LIST_SIZE, CollectionCategory.MAX_LIST_SIZE + 1);
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
