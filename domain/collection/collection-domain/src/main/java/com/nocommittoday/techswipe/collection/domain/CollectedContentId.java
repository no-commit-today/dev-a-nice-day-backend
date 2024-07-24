package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.content.domain.TechContent;

public record CollectedContentId(long value) {

    public TechContent.Id toTechContentId() {
        return new TechContent.Id(value);
    }
}
