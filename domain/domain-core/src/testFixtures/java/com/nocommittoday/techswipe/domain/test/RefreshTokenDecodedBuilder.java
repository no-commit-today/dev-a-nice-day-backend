package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.RefreshTokenDecoded;

public class RefreshTokenDecodedBuilder {

    public RefreshTokenDecodedBuilder() {
    }

    public static RefreshTokenDecoded valid(RefreshToken refreshToken) {
        return RefreshTokenDecoded.valid(refreshToken);
    }

    public static RefreshTokenDecoded valid() {
        return RefreshTokenDecoded.valid(RefreshTokenBuilder.create());
    }

    public static RefreshTokenDecoded invalid(Exception exception) {
        return RefreshTokenDecoded.invalid(exception);
    }

    public static RefreshTokenDecoded invalid() {
        return RefreshTokenDecoded.invalid(new RefreshTokenTestException());
    }

    public static class RefreshTokenTestException extends RuntimeException {
    }
}
