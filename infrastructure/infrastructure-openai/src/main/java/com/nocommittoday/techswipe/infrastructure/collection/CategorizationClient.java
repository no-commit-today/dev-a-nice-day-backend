package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;

interface CategorizationClient {

    String categorize(CollectedContent collectedContent);
}
