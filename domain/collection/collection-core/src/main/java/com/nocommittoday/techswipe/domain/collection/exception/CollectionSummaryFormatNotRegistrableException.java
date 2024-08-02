package com.nocommittoday.techswipe.domain.collection.exception;


import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionSummaryFormatNotRegistrableException extends AbstractDomainException {

    public CollectionSummaryFormatNotRegistrableException(CollectedContentId id, String summary) {
        super(CollectionErrorCode.SUMMARY_FORMAT_NOT_REGISTRABLE, "id=" + id + ", summary=" + summary);
    }
}
