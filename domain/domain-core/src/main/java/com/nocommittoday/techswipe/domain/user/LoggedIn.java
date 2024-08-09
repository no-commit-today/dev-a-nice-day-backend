package com.nocommittoday.techswipe.domain.user;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoggedIn {

    private final UUID uuid;

    private final LocalDateTime expiresAt;

    public LoggedIn(UUID uuid, LocalDateTime expiresAt) {
        this.uuid = uuid;
        this.expiresAt = expiresAt;
    }
}
