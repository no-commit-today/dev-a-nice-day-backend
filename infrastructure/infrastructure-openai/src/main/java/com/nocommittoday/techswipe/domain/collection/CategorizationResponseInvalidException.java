package com.nocommittoday.techswipe.domain.collection;

public class CategorizationResponseInvalidException extends RuntimeException {

    public CategorizationResponseInvalidException(String message, CollectedContentId id, String content) {
        super(message + "id=" + id + ", content=" + content);
    }
}
