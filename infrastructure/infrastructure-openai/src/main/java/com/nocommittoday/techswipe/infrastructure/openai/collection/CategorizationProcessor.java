package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class CategorizationProcessor {

    public static final int MIN_CATEGORY_NUM = 1;
    public static final int MAX_CATEGORY_NUM = 3;

    private final CategorizationClient categorizationClient;

    public CategorizationProcessor(CategorizationClient categorizationClient) {
        this.categorizationClient = categorizationClient;
    }

    @Retryable(retryFor = DomainValidationException.class)
    public CollectionCategoryList categorize(CollectedContent collectedContent) {
        String responseContent = categorizationClient.categorize(collectedContent);
        return CollectionCategoryList.create(responseContent);
    }
}
