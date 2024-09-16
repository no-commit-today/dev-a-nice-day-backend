package com.nocommittoday.techswipe.domain.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoggedInTest {

    @Test
    void 게터() {
        // given
        UUID uuid = UUID.randomUUID();
        LoggedIn loggedIn = new LoggedIn(
                new RefreshTokenId(uuid),
                LocalDateTime.of(2021, 1, 1, 0, 0)
        );

        // when
        // then
        assertThat(loggedIn.getRefreshTokenId()).isEqualTo(new RefreshTokenId(uuid));
        assertThat(loggedIn.getExpiresAt()).isEqualTo(LocalDateTime.of(2021, 1, 1, 0, 0));
    }
}