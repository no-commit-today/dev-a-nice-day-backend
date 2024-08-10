package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;

import javax.annotation.Nullable;

public class RefreshTokenDecoded {

    @Nullable
    private final RefreshToken refreshToken;

    @Nullable
    private final Exception exception;

    private RefreshTokenDecoded(
            @Nullable RefreshToken refreshToken, @Nullable Exception exception
    ) {
        this.refreshToken = refreshToken;
        this.exception = exception;
    }

    public static RefreshTokenDecoded valid(RefreshToken refreshToken) {
        return new RefreshTokenDecoded(refreshToken, null);
    }

    public static RefreshTokenDecoded invalid(Exception exception) {
        return new RefreshTokenDecoded(null, exception);
    }

    public RefreshToken verify() {
        if (this.exception != null) {
            throw new UserAuthenticationFailureException(this.exception);
        }
        return this.refreshToken;
    }
}
