package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.UserId;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class AccessTokenBuilder {

    @Nullable
    private String value;
    @Nullable
    private UserId userId;
    @Nullable
    private LocalDateTime issuedAt;
    @Nullable
    private LocalDateTime expiresAt;

    public static AccessToken create() {
        return new AccessTokenBuilder().build();
    }

    public AccessTokenBuilder() {
    }

    public AccessTokenBuilder value(String value) {
        this.value = value;
        return this;
    }

    public AccessTokenBuilder userId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public AccessTokenBuilder issuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public AccessTokenBuilder expiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public AccessToken build() {
        fillRequiredFields();
        return new AccessToken(value, userId, issuedAt, expiresAt);
    }

    private void fillRequiredFields() {
        if (value == null) {
            value = "access-token-" + LocalAutoIncrementIdUtils.nextId();
        }
        if (userId == null) {
            userId = new UserId(LocalAutoIncrementIdUtils.nextId());
        }
        if (issuedAt == null) {
            issuedAt = LocalDateTime.of(2021, 1, 1, 12, 30);
        }
        if (expiresAt == null) {
            expiresAt = issuedAt.plusDays(1);
        }
    }
}
