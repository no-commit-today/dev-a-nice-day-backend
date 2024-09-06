package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import com.nocommittoday.techswipe.storage.mysql.test.LoggedInEntityBuilder;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoggedInUserReaderTest {

    @InjectMocks
    private LoggedInUserReader loggedInUserReader;

    @Mock
    private LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    @Test
    void 리프레시_토큰을_통해_로그인_정보를_조회한다() {
        // given
        RefreshTokenId refreshTokenId = new RefreshTokenId(UUID.randomUUID());
        LoggedInEntity loggedInEntity = new LoggedInEntityBuilder()
                .refreshTokenId(refreshTokenId.value().toString())
                .build(true);
        given(loggedInEntityJpaRepository.findByRefreshTokenId(refreshTokenId.value().toString()))
                .willReturn(Optional.of(loggedInEntity));

        // when
        LoggedInUser loggedInUser = loggedInUserReader.get(refreshTokenId);

        // then
        assertThat(loggedInUser.getLoggedIn().getRefreshTokenId()).isEqualTo(refreshTokenId);
    }

    @Test
    void 리프레시_토큰을_통해_로그인_정보를_조회할_수_없는_경우_예외를_발생시킨다() {
        // given
        RefreshTokenId refreshTokenId = new RefreshTokenId(UUID.randomUUID());
        given(loggedInEntityJpaRepository.findByRefreshTokenId(refreshTokenId.value().toString()))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> loggedInUserReader.get(refreshTokenId))
                .isInstanceOf(UserAuthenticationFailureException.class);
    }

    @Test
    void 로그인_정보가_삭제된_경우_예외를_발생시킨다() {
        // given
        RefreshTokenId refreshTokenId = new RefreshTokenId(UUID.randomUUID());
        LoggedInEntity loggedInEntity = new LoggedInEntityBuilder()
                .refreshTokenId(refreshTokenId.value().toString())
                .build(true);
        loggedInEntity.delete();
        given(loggedInEntityJpaRepository.findByRefreshTokenId(refreshTokenId.value().toString()))
                .willReturn(Optional.of(loggedInEntity));

        // when, then
        assertThatThrownBy(() -> loggedInUserReader.get(refreshTokenId))
                .isInstanceOf(UserAuthenticationFailureException.class);
    }
}