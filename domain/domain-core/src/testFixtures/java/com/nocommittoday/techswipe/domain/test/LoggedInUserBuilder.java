package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.LoggedIn;
import com.nocommittoday.techswipe.domain.user.LoggedInUser;
import com.nocommittoday.techswipe.domain.user.UserId;

import javax.annotation.Nullable;

public class LoggedInUserBuilder {

    @Nullable
    private UserId id;

    @Nullable
    private LoggedIn loggedIn;

    public LoggedInUserBuilder() {
    }

    public static LoggedInUser create() {
        return new LoggedInUserBuilder().build();
    }

    public LoggedInUserBuilder id(UserId id) {
        this.id = id;
        return this;
    }

    public LoggedInUserBuilder loggedIn(LoggedIn loggedIn) {
        this.loggedIn = loggedIn;
        return this;
    }

    public LoggedInUser build() {
        fillRequiredFields();
        return new LoggedInUser(
            id,
            loggedIn
        );
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = new UserId(LocalAutoIncrementIdUtils.nextId());
        }
        if (loggedIn == null) {
            loggedIn = LoggedInBuilder.create();
        }
    }
}
