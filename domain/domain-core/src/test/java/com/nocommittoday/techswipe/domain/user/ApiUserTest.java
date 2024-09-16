package com.nocommittoday.techswipe.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiUserTest {

    @Test
    void 게터() {
        // given
        ApiUser apiUser = new ApiUser(new UserId(1L));

        // when
        // then
        assertThat(apiUser.getUserId()).isEqualTo(new UserId(1L));
    }
}