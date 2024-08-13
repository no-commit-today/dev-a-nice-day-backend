package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;

public class LoggedIn {

    private final RefreshTokenId refreshTokenId;

    private final LocalDateTime expiresAt;

    public LoggedIn(RefreshTokenId refreshTokenId, LocalDateTime expiresAt) {
        this.refreshTokenId = refreshTokenId;
        this.expiresAt = expiresAt;
    }

    public RefreshTokenId getRefreshTokenId() {
        return refreshTokenId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
