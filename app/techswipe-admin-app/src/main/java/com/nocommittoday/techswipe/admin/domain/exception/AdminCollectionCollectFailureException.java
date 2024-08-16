package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class AdminCollectionCollectFailureException extends AbstractDomainException {

    public AdminCollectionCollectFailureException(TechContentProviderId providerId) {
        super(AdminErrorCode.COLLECTION_COLLECT_FAILURE, "존재하지 않은 컨텐츠 제공자 providerId=" + providerId);
    }
}
