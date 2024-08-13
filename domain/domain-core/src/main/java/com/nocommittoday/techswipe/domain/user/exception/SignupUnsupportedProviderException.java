package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;

public class SignupUnsupportedProviderException extends AbstractDomainException {

    public SignupUnsupportedProviderException(OAuth2Provider provider) {
        super(UserErrorCode.SIGNUP_UNSUPPORTED_PROVIDER, "provider=" + provider);
    }
}
