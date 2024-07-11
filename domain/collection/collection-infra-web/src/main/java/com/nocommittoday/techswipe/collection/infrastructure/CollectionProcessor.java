package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;


public interface CollectionProcessor {

    int MIN_CATEGORY_NUM = 1;
    int MAX_CATEGORY_NUM = 3;

    int MIN_SUMMARY_LINE = 1;
    int MAX_SUMMARY_LINE = 5;

    CategorizationResult categorize(CollectedContent content);

    SummarizationResult summarize(CollectedContent content);
}
