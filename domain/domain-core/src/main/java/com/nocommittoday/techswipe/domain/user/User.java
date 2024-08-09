package com.nocommittoday.techswipe.domain.user;

public class User {

    private final UserId id;

    public User(UserId id) {
        this.id = id;
    }

    public LoggedInUser login(RefreshToken refreshToken) {
        return new LoggedInUser(id, new LoggedIn(refreshToken.getUuid(), refreshToken.getExpiresAt()));

    }

    public UserId getId() {
        return id;
    }
}
