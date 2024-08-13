package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.test.AccessTokenBuilder;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccessTokenDecodedTest {

    @Test
    void 유효한_토큰을_검증하면_토큰을_반환받는다() {
        // given
        AccessToken accessToken = AccessTokenBuilder.create();
        AccessTokenDecoded accessTokenDecoded = AccessTokenDecoded.valid(accessToken);

        // when
        AccessToken result = accessTokenDecoded.verify();

        // then
        Assertions.assertThat(result).isEqualTo(accessToken);
    }

    @Test
    void 유효하지_않은_토큰을_검증하면_예외가_발생한다() {
        // given
        AccessTokenDecoded accessTokenDecoded = AccessTokenDecoded.invalid(
                new RuntimeException("테스트")
        );

        // when
        // then
        assertThatThrownBy(() -> accessTokenDecoded.verify())
                .isInstanceOf(UserAuthenticationFailureException.class);
    }

}