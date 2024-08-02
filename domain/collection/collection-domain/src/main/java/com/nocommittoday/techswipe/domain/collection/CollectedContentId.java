package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.content.domain.TechContentId;

public record CollectedContentId(long value) {

    public TechContentId toTechContentId() {
        return new TechContentId(value);
    }
}
