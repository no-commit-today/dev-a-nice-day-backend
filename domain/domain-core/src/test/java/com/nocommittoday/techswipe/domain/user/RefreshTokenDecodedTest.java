package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.test.RefreshTokenBuilder;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RefreshTokenDecodedTest {

    @Test
    void 유효한_토큰을_검증하면_토큰을_반환받는다() {
        // given
        RefreshToken token = RefreshTokenBuilder.create();
        RefreshTokenDecoded decoded = RefreshTokenDecoded.valid(token);

        // when
        RefreshToken result = decoded.verify();

        // then
        Assertions.assertThat(result).isEqualTo(token);
    }

    @Test
    void 유효하지_않은_토큰을_검증하면_예외가_발생한다() {
        // given
        RefreshTokenDecoded decoded = RefreshTokenDecoded.invalid(
                new RuntimeException("테스트")
        );

        // when
        // then
        assertThatThrownBy(() -> decoded.verify())
                .isInstanceOf(UserAuthenticationFailureException.class);
    }
}