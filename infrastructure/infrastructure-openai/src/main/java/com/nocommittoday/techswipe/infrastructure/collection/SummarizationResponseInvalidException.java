package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;

public class SummarizationResponseInvalidException extends RuntimeException {

    public SummarizationResponseInvalidException(
            final String message, final CollectedContentId id, final String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
