package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class AuthenticationRequiredException extends AbstractDomainException {

    public AuthenticationRequiredException() {
        super(UserErrorCode.AUTHENTICATION_REQUIRED);
    }
}
