package com.nocommittoday.techswipe.domain.user;

public class AdminApiUser {

    private final UserId userId;

    public AdminApiUser(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }
}
