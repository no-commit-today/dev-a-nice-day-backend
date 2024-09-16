package com.nocommittoday.techswipe.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdminApiUserTest {

    @Test
    void 게터() {
        // given
        AdminApiUser adminApiUser = new AdminApiUser(new UserId(1L));

        // when
        // then
        assertThat(adminApiUser.getUserId()).isEqualTo(new UserId(1L));
    }
}