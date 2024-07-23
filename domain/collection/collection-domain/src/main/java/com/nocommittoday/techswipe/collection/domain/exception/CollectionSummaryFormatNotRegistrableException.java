package com.nocommittoday.techswipe.collection.domain.exception;


import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionSummaryFormatNotRegistrableException extends AbstractDomainException {

    public CollectionSummaryFormatNotRegistrableException(final CollectedContent.Id id, final String summary) {
        super(CollectionErrorCode.SUMMARY_FORMAT_NOT_REGISTRABLE, "id=" + id + ", summary=" + summary);
    }
}
