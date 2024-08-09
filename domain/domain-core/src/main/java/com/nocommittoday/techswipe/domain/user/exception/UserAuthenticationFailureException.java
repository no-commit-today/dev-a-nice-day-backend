package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Token;

public class UserAuthenticationFailureException extends AbstractDomainException {

    public UserAuthenticationFailureException(OAuth2Token token) {
        super(UserErrorCode.AUTHENTICATION_FAILURE, "OAuth2 provider: " + token.getProvider());
    }

}