package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

interface SummarizationClient {

    String summarize(CollectedContent collectedContent);
}
