package com.nocommittoday.techswipe.support;

import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import com.nocommittoday.techswipe.domain.user.AccessTokenDecoded;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import com.nocommittoday.techswipe.domain.user.exception.AuthenticationRequiredException;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtAccessTokenDecoder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.NativeWebRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ApiUserArgumentResolverTest {

    @InjectMocks
    private ApiUserArgumentResolver apiUserArgumentResolver;

    @Mock
    private JwtAccessTokenDecoder jwtAccessTokenDecoder;

    @Test
    void 인증된_사용자의_ApiUser_객체를_반환한다() throws Exception {
        // given
        AccessTokenDecoded accessTokenDecoded = AccessTokenDecodedBuilder.valid();
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(accessTokenDecoded);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        given(webRequest.getHeader("Authorization")).willReturn("Bearer access-token");

        // when
        ApiUser apiUser = (ApiUser) apiUserArgumentResolver
                .resolveArgument(null, null, webRequest, null);

        // then
        Assertions.assertThat(apiUser.getUserId())
                .isEqualTo(accessTokenDecoded.verify().getUserId());
    }

    @Test
    void 인증되지_않은_사용자의_요청은_AuthenticationRequiredException을_던진다() {
        // given
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        given(webRequest.getHeader("Authorization")).willReturn(null);

        // when
        Assertions.assertThatThrownBy(() -> apiUserArgumentResolver
                .resolveArgument(null, null, webRequest, null))
                .isInstanceOf(AuthenticationRequiredException.class);
    }

    @Test
    void 유효하지_않은_토큰의_요청은_AuthenticationFailedException을_던진다() {
        // given
        AccessTokenDecoded accessTokenDecoded = AccessTokenDecodedBuilder.invalid();
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(accessTokenDecoded);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        given(webRequest.getHeader("Authorization")).willReturn("Bearer access-token");

        // when
        Assertions.assertThatThrownBy(() -> apiUserArgumentResolver
                .resolveArgument(null, null, webRequest, null))
                .isInstanceOf(UserAuthenticationFailureException.class);
    }
}