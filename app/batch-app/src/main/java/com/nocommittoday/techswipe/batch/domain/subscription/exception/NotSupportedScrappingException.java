package com.nocommittoday.techswipe.batch.domain.subscription.exception;

import com.nocommittoday.techswipe.batch.domain.core.BatchDomainException;
import com.nocommittoday.techswipe.domain.subscription.ScrappingType;

public class NotSupportedScrappingException extends BatchDomainException {

    public NotSupportedScrappingException(ScrappingType type) {
        super("지원하지 않는 ScrappingType 입니다. type=" + type);
    }
}
