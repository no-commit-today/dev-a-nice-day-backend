package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;

public class AccessToken {

    private final String value;

    private final LocalDateTime issuedAt;

    private final LocalDateTime expiresAt;

    public AccessToken(String value, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
