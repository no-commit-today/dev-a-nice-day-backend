package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.content.TechContentId;

public record CollectedContentId(long value) {

    public TechContentId toTechContentId() {
        return new TechContentId(value);
    }
}
