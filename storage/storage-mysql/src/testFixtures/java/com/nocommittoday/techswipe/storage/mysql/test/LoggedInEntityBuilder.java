package com.nocommittoday.techswipe.storage.mysql.test;

import com.nocommittoday.techswipe.domain.test.LocalAutoIncrementIdUtils;
import com.nocommittoday.techswipe.domain.user.LoggedInUser;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

public class LoggedInEntityBuilder {

    @Nullable
    private Long id;

    @Nullable
    private UserEntity user;

    @Nullable
    private String refreshTokenId;

    @Nullable
    private LocalDateTime expiresAt;

    public LoggedInEntityBuilder() {
    }

    public static LoggedInEntity create() {
        return create(false);
    }

    public static LoggedInEntity create(boolean withId) {
        return new LoggedInEntityBuilder().build(withId);
    }

    public static LoggedInEntity from(LoggedInUser loggedInUser) {
        return new LoggedInEntityBuilder()
            .user(new UserEntityBuilder().id(loggedInUser.getId().value()).build())
            .refreshTokenId(loggedInUser.getLoggedIn().getRefreshTokenId().value().toString())
            .expiresAt(loggedInUser.getLoggedIn().getExpiresAt())
            .build();
    }

    public LoggedInEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public LoggedInEntityBuilder user(UserEntity user) {
        this.user = user;
        return this;
    }

    public LoggedInEntityBuilder refreshTokenId(String refreshTokenId) {
        this.refreshTokenId = refreshTokenId;
        return this;
    }

    public LoggedInEntityBuilder expiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public LoggedInEntity build() {
        return build(false);
    }

    public LoggedInEntity build(boolean withId) {
        fillRequiredFields(withId);
        return new LoggedInEntity(
            id,
            user,
            refreshTokenId,
            expiresAt
        );
    }

    private void fillRequiredFields(boolean withId) {
        if (withId && id == null) {
            id = LocalAutoIncrementIdUtils.nextId();
        }

        if (user == null) {
            user = UserEntityBuilder.create(withId);
        }

        if (refreshTokenId == null) {
            refreshTokenId = UUID.randomUUID().toString();
        }

        if (expiresAt == null) {
            expiresAt = LocalDateTime.now();
        }
    }

}
