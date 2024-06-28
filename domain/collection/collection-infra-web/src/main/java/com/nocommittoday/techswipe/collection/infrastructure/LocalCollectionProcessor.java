package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.Prompt;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class LocalCollectionProcessor implements CollectionProcessor {

    private final Random random = new Random();

    @Override
    public CategorizationResult categorize(final Prompt prompt, final CollectedContent content) {
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
    public SummarizationResult summarize(final Prompt prompt, final CollectedContent content) {
        final int numSummaryLine = random.nextInt(MAX_SUMMARY_LINE);
        return SummarizationResult.success(
                IntStream.rangeClosed(1, numSummaryLine)
                        .mapToObj(i -> "- summary-" + i)
                        .collect(Collectors.joining("\n"))
        );
    }
}
