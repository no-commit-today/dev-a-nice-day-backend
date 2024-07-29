package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public class CategorizationResponseInvalidException extends RuntimeException {

    public CategorizationResponseInvalidException(final String message, final CollectedContentId id, final String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
