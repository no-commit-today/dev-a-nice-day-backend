package com.nocommittoday.techswipe.collection.application.port.in;

import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;

public interface ContentCollectUseCase {

    void collect(final ContentCollect command);
}
