package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.UserId;

public class NotAllowedException extends AbstractDomainException {

    public NotAllowedException(UserId userId) {
        super(UserErrorCode.NOT_ALLOWED, "userId=" + userId);
    }
}
