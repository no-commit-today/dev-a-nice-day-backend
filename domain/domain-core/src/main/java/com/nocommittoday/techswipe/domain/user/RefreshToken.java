package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;

public class RefreshToken {

    private final RefreshTokenId id;

    private final String value;

    private final LocalDateTime issuedAt;

    private final LocalDateTime expiresAt;

    public RefreshToken(RefreshTokenId id, String value, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.id = id;
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public RefreshTokenId getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
