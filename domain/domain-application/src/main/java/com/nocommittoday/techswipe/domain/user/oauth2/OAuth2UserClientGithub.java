package com.nocommittoday.techswipe.domain.user.oauth2;

import com.nocommittoday.techswipe.client.oauth2.GithubOAuth2Client;
import com.nocommittoday.techswipe.client.oauth2.GithubOAuth2UserResponse;
import com.nocommittoday.techswipe.client.oauth2.OAuth2ClientResult;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import org.springframework.stereotype.Component;

@Component
public class OAuth2UserClientGithub implements OAuth2UserClient {

    private final GithubOAuth2Client githubOAuth2Client;

    public OAuth2UserClientGithub(GithubOAuth2Client githubOAuth2Client) {
        this.githubOAuth2Client = githubOAuth2Client;
    }

    @Override
    public boolean supports(OAuth2Token token) {
        return token instanceof OAuth2AccessToken && OAuth2Provider.GITHUB.equals(token.getProvider());
    }

    @Override
    public OAuth2User getUser(OAuth2Token token) {
        return doGetUser((OAuth2AccessToken) token);
    }

    private OAuth2User doGetUser(OAuth2AccessToken token) {
        OAuth2ClientResult<GithubOAuth2UserResponse> result = githubOAuth2Client.getUser(token.getAccessToken());
        if (result.isUnauthorized()) {
            throw new UserAuthenticationFailureException(token);
        }

        GithubOAuth2UserResponse response = result.getResponse();

        return new OAuth2User(
                token.getProvider(),
                response.id()
        );
    }
}
