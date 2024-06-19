package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.Prompt;


public interface CollectionProcessor {

    CategorizationResult categorize(Prompt prompt,String content);

    SummarizationResult summarize(Prompt prompt, String content);
}
