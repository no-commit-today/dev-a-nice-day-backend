package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public interface CollectedContentReaderPort {
    CollectedContent get(final CollectedContent.CollectedContentId id);
}
