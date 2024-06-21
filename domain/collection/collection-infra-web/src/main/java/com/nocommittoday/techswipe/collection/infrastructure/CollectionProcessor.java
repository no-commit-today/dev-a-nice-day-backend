package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.Prompt;


public interface CollectionProcessor {

    int MAX_SUMMARY_LINE = 5;

    int MAX_CATEGORY_NUM = 3;

    CategorizationResult categorize(Prompt prompt,String content);

    SummarizationResult summarize(Prompt prompt, String content);
}
