package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public class CategorizationResponseInvalidException extends RuntimeException {

    public CategorizationResponseInvalidException(String message, CollectedContentId id, String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
