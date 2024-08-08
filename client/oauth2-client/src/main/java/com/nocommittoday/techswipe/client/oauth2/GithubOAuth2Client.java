package com.nocommittoday.techswipe.client.oauth2;

import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class GithubOAuth2Client {

    private final GithubOAuth2Api githubOAuth2Api;

    public GithubOAuth2Client(GithubOAuth2Api githubOAuth2Api) {
        this.githubOAuth2Api = githubOAuth2Api;
    }

    public OAuth2ClientResult<GithubOAuth2UserResponse> getUser(String accessToken) {
        try {
            return OAuth2ClientResult.ok(
                    githubOAuth2Api.getUser("Bearer " + accessToken)
            );
        } catch (FeignException.Unauthorized ex) {
            return OAuth2ClientResult.unauthorized(ex);
        }
    }
}
