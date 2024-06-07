package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public interface CollectedContentUpdatePort {
    void update(final CollectedContent collectedContent);
}
