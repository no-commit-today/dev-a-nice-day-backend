package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.domain.user.LoggedInUser;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.storage.mysql.test.LoggedInEntityBuilder;
import com.nocommittoday.techswipe.storage.mysql.test.UserEntityBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoggedInEntityTest {

    @Test
    void 새로운_객체를_생성한다() {
        // given
        UserEntity userEntity = UserEntityBuilder.create();
        RefreshTokenId refreshTokenId = new RefreshTokenId(UUID.randomUUID());
        LocalDateTime expiresAt = LocalDateTime.of(2021, 1, 1, 0, 0);

        // when
        LoggedInEntity result = LoggedInEntity.create(userEntity, refreshTokenId, expiresAt);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getUser()).isEqualTo(userEntity);
        assertThat(result.getRefreshTokenId()).isEqualTo(refreshTokenId.value().toString());
        assertThat(result.getExpiresAt()).isEqualTo(expiresAt);
    }

    @Test
    void 도메인_객체로_변환한다() {
        // given
        LoggedInEntity entity = LoggedInEntityBuilder.create(true);

        // when
        LoggedInUser result = entity.toDomain();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId().value()).isEqualTo(entity.getUser().getId());
        assertThat(result.getLoggedIn().getRefreshTokenId().value().toString()).isEqualTo(entity.getRefreshTokenId());
        assertThat(result.getLoggedIn().getExpiresAt()).isEqualTo(entity.getExpiresAt());
    }
}