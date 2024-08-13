package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.LoggedIn;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoggedInBuilder {

    @Nullable
    private RefreshTokenId refreshTokenId;

    @Nullable
    private LocalDateTime expiresAt;

    public LoggedInBuilder() {
    }

    public static LoggedIn create() {
        return new LoggedInBuilder().build();
    }

    public LoggedInBuilder refreshTokenId(RefreshTokenId refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
        return this;
    }

    public LoggedInBuilder expiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public LoggedIn build() {
        fillRequiredFields();
        return new LoggedIn(
            refreshTokenId,
            expiresAt
        );
    }

    private void fillRequiredFields() {
        if (refreshTokenId == null) {
            refreshTokenId = new RefreshTokenId(UUID.randomUUID());
        }
        if (expiresAt == null) {
            expiresAt = LocalDateTime.now().plusDays(1);
        }
    }
}
