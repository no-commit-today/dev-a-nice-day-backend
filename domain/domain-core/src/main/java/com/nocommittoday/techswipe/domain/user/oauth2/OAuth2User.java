package com.nocommittoday.techswipe.domain.user.oauth2;

public class OAuth2User {

    private final OAuth2Provider provider;
    private final String id;

    public OAuth2User(OAuth2Provider provider, String id) {
        this.provider = provider;
        this.id = id;
    }

    public OAuth2Provider getProvider() {
        return provider;
    }

    public String getId() {
        return id;
    }
}
