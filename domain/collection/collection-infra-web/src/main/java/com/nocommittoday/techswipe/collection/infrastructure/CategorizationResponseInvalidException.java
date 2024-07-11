package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public class CategorizationResponseInvalidException extends RuntimeException {

    public CategorizationResponseInvalidException(final String message, final CollectedContent.Id id, final String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
