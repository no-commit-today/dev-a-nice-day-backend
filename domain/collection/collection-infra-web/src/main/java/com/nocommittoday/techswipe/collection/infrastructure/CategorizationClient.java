package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

interface CategorizationClient {

    String categorize(CollectedContent collectedContent);
}
