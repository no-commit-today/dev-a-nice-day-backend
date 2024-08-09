package com.nocommittoday.techswipe.domain.user.oauth2;

public enum OAuth2Provider {

    GITHUB
    ;

    public boolean canSignUp() {
        return this.equals(GITHUB);
    }
}
