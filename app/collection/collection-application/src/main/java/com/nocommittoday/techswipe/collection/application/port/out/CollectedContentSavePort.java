package com.nocommittoday.techswipe.collection.application.port.out;

import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;

public interface CollectedContentSavePort {

    void save(final ContentCollect contentCollect);
}
