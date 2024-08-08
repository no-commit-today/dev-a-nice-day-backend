package com.nocommittoday.techswipe.domain.user.oauth2;

public interface OAuth2Client {

    boolean supports(OAuth2Token token);

    OAuth2User getUser(OAuth2Token token);
}
