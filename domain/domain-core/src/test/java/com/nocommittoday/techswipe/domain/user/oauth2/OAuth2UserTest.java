package com.nocommittoday.techswipe.domain.user.oauth2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2UserTest {

    @Test
    void 게터() {
        // given
        OAuth2User oAuth2User = new OAuth2User(OAuth2Provider.GITHUB, "id");

        // when
        // then
        assertThat(oAuth2User.getProvider()).isEqualTo(OAuth2Provider.GITHUB);
        assertThat(oAuth2User.getId()).isEqualTo("id");
    }
}