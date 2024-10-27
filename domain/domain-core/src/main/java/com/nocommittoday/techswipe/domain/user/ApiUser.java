package com.nocommittoday.techswipe.domain.user;

import java.util.Objects;

public class ApiUser {

    private final UserId userId;

    public ApiUser(UserId userId) {
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ApiUser apiUser = (ApiUser) object;
        return Objects.equals(userId, apiUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
