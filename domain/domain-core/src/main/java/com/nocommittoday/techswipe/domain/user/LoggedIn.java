package com.nocommittoday.techswipe.domain.user;

public class LoggedIn {

    private final LoggedInId id;

    private final UserId userId;

    private final RefreshToken refreshToken;

    public LoggedIn(LoggedInId id, UserId userId, RefreshToken refreshToken) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
