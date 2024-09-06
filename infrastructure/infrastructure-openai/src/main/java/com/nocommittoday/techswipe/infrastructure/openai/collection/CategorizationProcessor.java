package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CategorizationProcessor {

    public static final int MIN_CATEGORY_NUM = 1;
    public static final int MAX_CATEGORY_NUM = 3;

    private static final Pattern RESULT_PATTERN = Pattern.compile("^- ("
            + Arrays.stream(CollectionCategory.values())
            .map(CollectionCategory::name)
            .collect(Collectors.joining("|"))
            + ")$");

    private final CategorizationClient categorizationClient;

    public CategorizationProcessor(CategorizationClient categorizationClient) {
        this.categorizationClient = categorizationClient;
    }

    public CategorizationResult categorize(CollectedContent collectedContent) {
        try {
            String responseContent = categorizationClient.categorize(collectedContent);

            CollectionCategoryList categoryList = CollectionCategoryList.create(responseContent);

            return CategorizationResult.success(categoryList);
        } catch (Exception e) {
            return CategorizationResult.failure(e);
        }
    }
}
