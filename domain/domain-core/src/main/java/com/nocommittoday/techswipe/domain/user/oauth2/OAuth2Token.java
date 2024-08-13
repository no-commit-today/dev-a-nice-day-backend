package com.nocommittoday.techswipe.domain.user.oauth2;

public abstract class OAuth2Token {

    private final OAuth2Provider provider;

    protected OAuth2Token(OAuth2Provider provider) {
        this.provider = provider;
    }

    public OAuth2Provider getProvider() {
        return provider;
    }

    public static OAuth2AccessToken accessToken(OAuth2Provider provider, String accessToken) {
        return new OAuth2AccessToken(provider, accessToken);
    }
}
