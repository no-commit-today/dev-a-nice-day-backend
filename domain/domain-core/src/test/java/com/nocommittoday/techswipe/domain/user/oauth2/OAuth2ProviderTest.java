package com.nocommittoday.techswipe.domain.user.oauth2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2ProviderTest {

    @Test
    void value() {
        assertThat(OAuth2Provider.GITHUB).isNotNull();
    }

    @Test
    void 회원가입_가능_여부() {
        // given
        // when
        // then
        assertThat(OAuth2Provider.GITHUB.canSignUp()).isTrue();
    }
}