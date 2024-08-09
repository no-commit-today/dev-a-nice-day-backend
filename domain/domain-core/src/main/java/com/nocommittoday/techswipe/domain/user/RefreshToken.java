package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshToken {

    private final UUID uuid;

    private final String value;

    private final LocalDateTime issuedAt;

    private final LocalDateTime expiresAt;

    public RefreshToken(UUID uuid, String value, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.uuid = uuid;
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public UUID getUuid() {
        return uuid;
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
