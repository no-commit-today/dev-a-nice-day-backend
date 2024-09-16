package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Token;

import java.util.Map;

public class UserAuthenticationFailureException extends AbstractDomainException {

    public UserAuthenticationFailureException(OAuth2Token token) {
        super(UserErrorCode.AUTHENTICATION_FAILURE, Map.of("provider", token.getProvider()));
    }

    public UserAuthenticationFailureException(UserId userId) {
        super(UserErrorCode.AUTHENTICATION_FAILURE, Map.of("id", userId));
    }

    public UserAuthenticationFailureException(RefreshTokenId refreshTokenId) {
        super(UserErrorCode.AUTHENTICATION_FAILURE, Map.of("refreshTokenId", refreshTokenId));
    }

    public UserAuthenticationFailureException(UserId userId, RefreshTokenId refreshTokenId) {
        super(UserErrorCode.AUTHENTICATION_FAILURE, Map.of("id", userId, "refreshTokenId", refreshTokenId));
    }

    public UserAuthenticationFailureException(Exception exception) {
        super(UserErrorCode.AUTHENTICATION_FAILURE, exception, null);
    }
}
