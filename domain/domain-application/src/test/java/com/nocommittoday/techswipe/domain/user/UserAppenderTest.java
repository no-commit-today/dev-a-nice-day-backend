package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.test.OAuth2UserBuilder;
import com.nocommittoday.techswipe.domain.user.exception.AlreadyExistsException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;
import com.nocommittoday.techswipe.storage.mysql.test.UserEntityBuilder;
import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntityJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserAppenderTest {

    @InjectMocks
    private UserAppender userAppender;

    @Mock
    private UserEntityJpaRepository userEntityJpaRepository;

    @Mock
    private OAuth2UserEntityJpaRepository oAuth2UserEntityJpaRepository;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Captor
    private ArgumentCaptor<OAuth2UserEntity> oAuth2UserEntityCaptor;

    @Test
    void OAuth2User를_통해서_새로운_사용자를_추가한다() {
        // given
        OAuth2User oAuth2User = OAuth2UserBuilder.create();
        given(oAuth2UserEntityJpaRepository.existsByOauth2ProviderAndOauth2Id(oAuth2User.getProvider(), oAuth2User.getId()))
                .willReturn(false);

        UserEntity userEntity = UserEntityBuilder.create(true);
        given(userEntityJpaRepository.save(userEntityCaptor.capture()))
                .willReturn(userEntity);


        // when
        UserId userId = userAppender.append(oAuth2User);

        // then
        then(oAuth2UserEntityJpaRepository).should()
                .save(oAuth2UserEntityCaptor.capture());
        assertThat(userId).isEqualTo(new UserId(userEntity.getId()));
        assertThat(userEntityCaptor.getValue().getId()).isNull();
        assertThat(userEntityCaptor.getValue().isUsed()).isTrue();
        assertThat(oAuth2UserEntityCaptor.getValue().getOauth2Provider()).isEqualTo(oAuth2User.getProvider());
        assertThat(oAuth2UserEntityCaptor.getValue().getOauth2Id()).isEqualTo(oAuth2User.getId());
        assertThat(oAuth2UserEntityCaptor.getValue().getUser()).isEqualTo(userEntity);
    }


    @Test
    void OAuth2User를_통해서_이미_존재하는_사용자를_추가하려고_하는_경우_예외를_발생시킨다() {
        // given
        OAuth2User oAuth2User = OAuth2UserBuilder.create();
        given(oAuth2UserEntityJpaRepository.existsByOauth2ProviderAndOauth2Id(oAuth2User.getProvider(), oAuth2User.getId()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> userAppender.append(oAuth2User))
                .isInstanceOf(AlreadyExistsException.class);
    }
}