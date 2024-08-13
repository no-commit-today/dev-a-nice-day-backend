package com.nocommittoday.techswipe.domain.user;

import javax.annotation.Nullable;
import java.util.Objects;

public class ApiUserOrGuest {

    @Nullable
    private final UserId userId;

    private ApiUserOrGuest(@Nullable UserId userId) {
        this.userId = userId;
    }

    public static ApiUserOrGuest guest() {
        return new ApiUserOrGuest(null);
    }

    public static ApiUserOrGuest user(UserId userId) {
        return new ApiUserOrGuest(userId);
    }

    public boolean isGuest() {
        return userId == null;
    }

    public boolean isUser() {
        return userId != null;
    }

    public UserId getUserId() {
        return Objects.requireNonNull(userId);
    }
}
