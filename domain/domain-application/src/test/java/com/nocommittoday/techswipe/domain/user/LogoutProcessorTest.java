package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        // when
        logoutProcessor.process(loggedInUser);

        // then
        verify(loggedInEntityJpaRepository).findByRefreshTokenId(loggedInUser.getLoggedIn().getRefreshTokenId().value().toString());
    }
}