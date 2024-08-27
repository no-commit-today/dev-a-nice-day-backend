package com.nocommittoday.techswipe.controller.user.v1.response;

import com.nocommittoday.techswipe.domain.user.LoginResult;

import java.time.LocalDateTime;

public record LoginResponse(
        long userId,
        String accessToken,
        LocalDateTime accessTokenIssuedAt,
        LocalDateTime accessTokenExpiresAt,
        String refreshToken,
        LocalDateTime refreshTokenIssuedAt,
        LocalDateTime refreshTokenExpiresAt
) {

    public static LoginResponse from(LoginResult loginResult) {
        return new LoginResponse(
                loginResult.userId().value(),
                loginResult.accessToken().getValue(),
                loginResult.accessToken().getIssuedAt(),
                loginResult.accessToken().getExpiresAt(),
                loginResult.refreshToken().getValue(),
                loginResult.refreshToken().getIssuedAt(),
                loginResult.refreshToken().getExpiresAt()
        );
    }
}
