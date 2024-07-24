package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public class SummarizationResponseInvalidException extends RuntimeException {

    public SummarizationResponseInvalidException(
            final String message, final CollectedContentId id, final String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
