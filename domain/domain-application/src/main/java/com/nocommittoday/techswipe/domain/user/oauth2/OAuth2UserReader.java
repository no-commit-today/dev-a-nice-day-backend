package com.nocommittoday.techswipe.domain.user.oauth2;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OAuth2UserReader {

    private final List<OAuth2UserClient> userClients;

    public OAuth2UserReader(List<OAuth2UserClient> userClients) {
        this.userClients = userClients;
    }

    public OAuth2User read(OAuth2Token token) {
        return userClients.stream()
                .filter(client -> client.supports(token))
                .findFirst()
                .map(client -> client.getUser(token))
                .orElseThrow(() -> new UserAuthenticationFailureException(token));
    }
}
