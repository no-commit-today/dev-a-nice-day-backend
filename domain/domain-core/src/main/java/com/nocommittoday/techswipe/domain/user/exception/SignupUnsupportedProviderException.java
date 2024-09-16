package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;

import java.util.Map;

public class SignupUnsupportedProviderException extends AbstractDomainException {

    public SignupUnsupportedProviderException(OAuth2Provider provider) {
        super(UserErrorCode.SIGNUP_UNSUPPORTED_PROVIDER, Map.of("provider", provider));
    }
}
