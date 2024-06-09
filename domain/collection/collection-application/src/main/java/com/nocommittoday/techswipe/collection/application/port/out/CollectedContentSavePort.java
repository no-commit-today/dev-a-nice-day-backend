package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public interface CollectedContentSavePort {

    void save(final CollectedContent collectedContent);
}
