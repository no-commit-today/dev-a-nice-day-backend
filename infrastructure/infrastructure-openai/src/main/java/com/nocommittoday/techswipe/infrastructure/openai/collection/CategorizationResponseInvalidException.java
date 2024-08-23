package com.nocommittoday.techswipe.infrastructure.openai.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;

public class CategorizationResponseInvalidException extends RuntimeException {

    public CategorizationResponseInvalidException(String message, CollectedContentId id, String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
