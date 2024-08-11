package com.nocommittoday.techswipe.domain.user;

public class ApiUser {

    private final UserId userId;

    public ApiUser(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }
}
