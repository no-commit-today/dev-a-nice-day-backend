package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.test.LoggedInUserBuilder;
import com.nocommittoday.techswipe.storage.mysql.test.LoggedInEntityBuilder;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LogoutProcessorTest {

    @InjectMocks
    private LogoutProcessor logoutProcessor;

    @Mock
    private LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    @Test
    void 로그인_정보를_삭제한다() {
        // given
        LoggedInUser loggedInUser = LoggedInUserBuilder.create();
        LoggedInEntity loggedInEntity = LoggedInEntityBuilder.from(loggedInUser);
        given(loggedInEntityJpaRepository.findByRefreshTokenId(
                loggedInUser.getLoggedIn().getRefreshTokenId().value().toString()
        )).willReturn(Optional.of(loggedInEntity));

        // when
        logoutProcessor.process(loggedInUser);

        // then
        assertThat(loggedInEntity.isDeleted()).isTrue();
    }

    @Test
    void 로그인_정보가_존재하지_않는_경우_아무_일도_하지_않는다() {
        // given
        LoggedInUser loggedInUser = LoggedInUserBuilder.create();
        given(loggedInEntityJpaRepository.findByRefreshTokenId(
                loggedInUser.getLoggedIn().getRefreshTokenId().value().toString()
        )).willReturn(Optional.empty());

        // when
        // then
        logoutProcessor.process(loggedInUser);
    }
}