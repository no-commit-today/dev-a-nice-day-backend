package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.enums.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionPromptNotFoundException extends AbstractDomainException {

    public CollectionPromptNotFoundException(final PromptType type, final TechContentProviderType providerType) {
        super(CollectionErrorCode.PROMPT_NOT_FOUND, "type=" + type + ", providerType=" + providerType);
    }
}