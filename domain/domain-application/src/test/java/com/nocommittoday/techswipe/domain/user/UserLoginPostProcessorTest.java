package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.test.LoggedInUserBuilder;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import com.nocommittoday.techswipe.storage.mysql.test.UserEntityBuilder;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntity;
import com.nocommittoday.techswipe.storage.mysql.user.LoggedInEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntityJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserLoginPostProcessorTest {

    @InjectMocks
    private UserLoginPostProcessor userLoginPostProcessor;

    @Mock
    private UserEntityJpaRepository userEntityJpaRepository;

    @Mock
    private LoggedInEntityJpaRepository loggedInEntityJpaRepository;

    @Captor
    private ArgumentCaptor<LoggedInEntity> captor;

    @Test
    void 로그인에_성공하면_해당_로그인_정보를_저장한다() {
        // given
        LoggedInUser loggedInUser = LoggedInUserBuilder.create();

        UserEntity userEntity = new UserEntityBuilder()
                .id(loggedInUser.getId().value())
                .build();
        given(userEntityJpaRepository.findById(loggedInUser.getId().value()))
                .willReturn(Optional.of(userEntity));

        // when
        userLoginPostProcessor.process(loggedInUser);

        // then
        then(loggedInEntityJpaRepository).should().save(captor.capture());
        LoggedInEntity loggedInEntity = captor.getValue();
        assertThat(loggedInEntity.getId()).isNull();
        assertThat(loggedInEntity.getUser()).isEqualTo(userEntity);
    }

    @Test
    void 유저가_존재하지_않으면_예외를_던진다() {
        // given
        LoggedInUser loggedInUser = LoggedInUserBuilder.create();
        given(userEntityJpaRepository.findById(loggedInUser.getId().value()))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userLoginPostProcessor.process(loggedInUser))
                .isInstanceOf(UserAuthenticationFailureException.class);
    }

    @Test
    void 유저가_삭제되었으면_예외를_던진다() {
        // given
        LoggedInUser loggedInUser = LoggedInUserBuilder.create();
        UserEntity userEntity = new UserEntityBuilder()
                .id(loggedInUser.getId().value())
                .build();
        userEntity.delete();
        given(userEntityJpaRepository.findById(loggedInUser.getId().value()))
                .willReturn(Optional.of(userEntity));

        // when
        // then
        assertThatThrownBy(() -> userLoginPostProcessor.process(loggedInUser))
                .isInstanceOf(UserAuthenticationFailureException.class);
    }

    @Test
    void 존재하는_리프레쉬_토큰_아이디일_경우_예외를_던진다() {
        // given
        LoggedInUser loggedInUser = LoggedInUserBuilder.create();
        given(userEntityJpaRepository.findById(loggedInUser.getId().value()))
                .willReturn(Optional.of(new UserEntityBuilder().id(loggedInUser.getId().value()).build()));
        given(loggedInEntityJpaRepository.existsByRefreshTokenId(
                loggedInUser.getLoggedIn().getRefreshTokenId().value().toString())
        ).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> userLoginPostProcessor.process(loggedInUser))
                .isInstanceOf(UserAuthenticationFailureException.class);
    }
}