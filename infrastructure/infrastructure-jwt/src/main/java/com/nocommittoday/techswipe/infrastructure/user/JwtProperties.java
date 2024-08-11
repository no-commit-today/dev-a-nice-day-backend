package com.nocommittoday.techswipe.infrastructure.user;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Nullable;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        AccessToken accessToken,
        RefreshToken refreshToken
) {

    record AccessToken(
            @Nullable String secret,
            @Nullable Long expiration
    ) {

        public Long expirationOrDefault() {
            return expiration == null ? 3600L : expiration;
        }
    }

    record RefreshToken(
            @Nullable String secret,
            @Nullable Long expiration
    ) {

        public Long expirationOrDefault() {
            return expiration == null ? 36000L : expiration;
        }
    }
}
