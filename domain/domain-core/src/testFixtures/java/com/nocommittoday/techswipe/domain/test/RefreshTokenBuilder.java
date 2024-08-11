package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.domain.user.UserId;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshTokenBuilder {

    @Nullable
    private RefreshTokenId id;
    @Nullable
    private UserId userId;
    @Nullable
    private String value;
    @Nullable
    private LocalDateTime issuedAt;
    @Nullable
    private LocalDateTime expiresAt;

    public static RefreshToken create() {
        return new RefreshTokenBuilder().build();
    }

    public RefreshTokenBuilder() {
    }

    public RefreshTokenBuilder id(RefreshTokenId id) {
        this.id = id;
        return this;
    }

    public RefreshTokenBuilder value(String value) {
        this.value = value;
        return this;
    }

    public RefreshTokenBuilder userId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public RefreshTokenBuilder issuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public RefreshTokenBuilder expiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public RefreshToken build() {
        fillRequiredFields();
        return new RefreshToken(value, id, userId, issuedAt, expiresAt);
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = new RefreshTokenId(UUID.randomUUID());
        }
        if (value == null) {
            value = "refresh-token-" + LocalAutoIncrementIdUtils.nextId();
        }
        if (userId == null) {
            userId = new UserId(LocalAutoIncrementIdUtils.nextId());
        }
        if (issuedAt == null) {
            issuedAt = LocalDateTime.now();
        }
        if (expiresAt == null) {
            expiresAt = LocalDateTime.now().plusDays(1);
        }
    }
}
