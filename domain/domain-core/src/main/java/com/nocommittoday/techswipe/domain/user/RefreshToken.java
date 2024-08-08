package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;

public class RefreshToken {

    private final String value;

    private final LocalDateTime issuedAt;

    private final LocalDateTime expiresAt;

    public RefreshToken(String value, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
