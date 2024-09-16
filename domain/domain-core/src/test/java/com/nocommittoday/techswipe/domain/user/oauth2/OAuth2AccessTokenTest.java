package com.nocommittoday.techswipe.domain.user.oauth2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2AccessTokenTest {

    @Test
    void 게터() {
        // given
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2Provider.GITHUB, "accessToken");

        // when
        // then
        assertThat(oAuth2AccessToken.getProvider()).isEqualTo(OAuth2Provider.GITHUB);
        assertThat(oAuth2AccessToken.getAccessToken()).isEqualTo("accessToken");
    }
}