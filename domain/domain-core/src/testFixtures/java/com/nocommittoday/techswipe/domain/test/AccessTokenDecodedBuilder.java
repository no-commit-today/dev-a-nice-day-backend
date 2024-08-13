package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.AccessTokenDecoded;

public class AccessTokenDecodedBuilder {

    public AccessTokenDecodedBuilder() {
    }

    public static AccessTokenDecoded valid(AccessToken accessToken) {
        return AccessTokenDecoded.valid(accessToken);
    }

    public static AccessTokenDecoded valid() {
        return AccessTokenDecoded.valid(AccessTokenBuilder.create());
    }

    public static AccessTokenDecoded invalid(Exception exception) {
        return AccessTokenDecoded.invalid(exception);
    }

    public static AccessTokenDecoded invalid() {
        return AccessTokenDecoded.invalid(new AccessTokenTestException());
    }

    public static class AccessTokenTestException extends RuntimeException {
    }
}
