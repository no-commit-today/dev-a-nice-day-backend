package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionSummaryFormatNotRegistrableException extends AbstractDomainException {

    public CollectionSummaryFormatNotRegistrableException(final CollectedContent.Id id, final String summary) {
        super(CollectionErrorCode.SUMMARY_FORMAT_NOT_REGISTRABLE, "id=" + id + ", summary=" + summary);
    }
}
