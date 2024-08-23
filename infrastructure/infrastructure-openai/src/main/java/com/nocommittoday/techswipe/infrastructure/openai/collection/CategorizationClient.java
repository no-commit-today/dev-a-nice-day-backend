package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;

interface CategorizationClient {

    String categorize(CollectedContent collectedContent);
}
