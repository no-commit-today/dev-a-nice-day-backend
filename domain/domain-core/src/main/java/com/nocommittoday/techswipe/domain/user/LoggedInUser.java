package com.nocommittoday.techswipe.domain.user;

public class LoggedInUser {

    private final UserId id;

    private final LoggedIn loggedIn;

    public LoggedInUser(UserId id, LoggedIn loggedIn) {
        this.id = id;
        this.loggedIn = loggedIn;
    }

    public UserId getId() {
        return id;
    }

    public LoggedIn getLoggedIn() {
        return loggedIn;
    }
}
