package com.nocommittoday.techswipe.collection.application.port.in;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public interface CollectionCategorizeUseCase {

    void categorize(CollectedContent.CollectedContentId id);
}
