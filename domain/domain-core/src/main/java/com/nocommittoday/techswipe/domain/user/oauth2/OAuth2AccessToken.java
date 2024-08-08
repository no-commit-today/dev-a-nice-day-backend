package com.nocommittoday.techswipe.domain.user.oauth2;

public class OAuth2AccessToken extends OAuth2Token {

    private final String accessToken;

    public OAuth2AccessToken(OAuth2Provider provider, String accessToken) {
        super(provider);
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
