package com.nocommittoday.techswipe.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiUserOrGuestTest {

    @Test
    void 게스트_유저를_생성할_수_있다() {
        // given
        ApiUserOrGuest user = ApiUserOrGuest.guest();

        // when
        // then
        assertThat(user.isGuest()).isTrue();
        assertThat(user.isUser()).isFalse();
    }

    @Test
    void 사용자_유저를_생성할_수_있다() {
        // given
        UserId userId = new UserId(1L);
        ApiUserOrGuest user = ApiUserOrGuest.user(userId);

        // when
        // then
        assertThat(user.isGuest()).isFalse();
        assertThat(user.isUser()).isTrue();
        assertThat(user.getUserId()).isEqualTo(userId);
    }
}