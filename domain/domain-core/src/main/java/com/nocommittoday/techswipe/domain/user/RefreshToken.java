package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;

public class RefreshToken {

    private final RefreshTokenId id;

    private final UserId userId;

    private final String value;

    private final LocalDateTime issuedAt;

    private final LocalDateTime expiresAt;

    public RefreshToken(
            String value, RefreshTokenId id, UserId userId, LocalDateTime issuedAt, LocalDateTime expiresAt
    ) {
        this.id = id;
        this.userId = userId;
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getValue() {
        return value;
    }

    public RefreshTokenId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
