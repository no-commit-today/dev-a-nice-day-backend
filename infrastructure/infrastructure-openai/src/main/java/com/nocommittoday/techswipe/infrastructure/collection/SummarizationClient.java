package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;

interface SummarizationClient {

    String summarize(CollectedContent collectedContent);
}
