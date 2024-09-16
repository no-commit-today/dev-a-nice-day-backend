package com.nocommittoday.techswipe.domain.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AccessTokenTest {

    @Test
    void 게터() {
        // given
        AccessToken accessToken = new AccessToken(
                "token",
                new UserId(1L),
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2021, 1, 1, 0, 30)
        );

        // when
        // then
        assertThat(accessToken.getValue()).isEqualTo("token");
        assertThat(accessToken.getUserId()).isEqualTo(new UserId(1L));
        assertThat(accessToken.getIssuedAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0));
        assertThat(accessToken.getExpiresAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 30));
    }
}