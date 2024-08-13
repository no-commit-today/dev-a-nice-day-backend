package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;

public class AccessToken {

    private final String value;

    private final UserId userId;

    private final LocalDateTime issuedAt;

    private final LocalDateTime expiresAt;

    public AccessToken(String value, UserId userId, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.value = value;
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getValue() {
        return value;
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
