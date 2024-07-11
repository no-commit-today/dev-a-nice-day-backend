package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public class SummarizationResponseInvalidException extends RuntimeException {

    public SummarizationResponseInvalidException(
            final String message, final CollectedContent.Id id, final String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
