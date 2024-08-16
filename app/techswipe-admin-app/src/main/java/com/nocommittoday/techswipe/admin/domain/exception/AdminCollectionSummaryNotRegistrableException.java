package com.nocommittoday.techswipe.admin.domain.exception;


import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class AdminCollectionSummaryNotRegistrableException extends AbstractDomainException {

    public AdminCollectionSummaryNotRegistrableException(CollectedContentId id, Summary summary) {
        super(AdminErrorCode.COLLECTION_SUMMARY_NOT_REGISTRABLE, "id=" + id + ", summary=\n" + summary.getContent());
    }
}
