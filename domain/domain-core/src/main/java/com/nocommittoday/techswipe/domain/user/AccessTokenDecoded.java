package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;

import javax.annotation.Nullable;
import java.util.Objects;

public class AccessTokenDecoded {

    @Nullable
    private final AccessToken accessToken;

    @Nullable
    private final Exception exception;

    private AccessTokenDecoded(
            @Nullable AccessToken accessToken, @Nullable Exception exception
    ) {
        this.accessToken = accessToken;
        this.exception = exception;
    }

    public static AccessTokenDecoded valid(AccessToken accessToken) {
        return new AccessTokenDecoded(accessToken, null);
    }

    public static AccessTokenDecoded invalid(Exception exception) {
        return new AccessTokenDecoded(null, exception);
    }

    public AccessToken verify() {
        if (this.exception != null) {
            throw new UserAuthenticationFailureException(this.exception);
        }
        return Objects.requireNonNull(this.accessToken);
    }
}
