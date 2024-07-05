package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;


public interface CollectionProcessor {

    int MAX_SUMMARY_LINE = 5;

    int MAX_CATEGORY_NUM = 3;

    CategorizationResult categorize(CollectedContent content);

    SummarizationResult summarize(CollectedContent content);
}
