package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.LoginRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.test.AccessTokenBuilder;
import com.nocommittoday.techswipe.domain.test.RefreshTokenBuilder;
import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.LoginResult;
import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.UserLoginService;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtAccessTokenEncoder;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtRefreshTokenEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserLoginController.class)
class UserLoginControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    private JwtAccessTokenEncoder jwtAccessTokenEncoder;

    @MockBean
    private JwtRefreshTokenEncoder jwtRefreshTokenEncoder;

    @Test
    void 로그인_Docs() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("oauth2-access-token");
        UserId userId = new UserId(1L);
        AccessToken accessToken = new AccessTokenBuilder().userId(userId).build();
        RefreshToken refreshToken = new RefreshTokenBuilder().userId(userId).build();
        given(jwtAccessTokenEncoder.encode(userId)).willReturn(accessToken);
        given(jwtRefreshTokenEncoder.encode(userId)).willReturn(refreshToken);
        given(userLoginService.login(
                any(), any(), any())
        ).willReturn(new LoginResult(userId, accessToken, refreshToken));

        // when
        // then
        mockMvc.perform(post("/api/user/v1/login")
                        .content(objectMapper.writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("user/login",
                        requestFields(
                                fieldWithPath("accessToken")
                                        .description("OAuth2 액세스 토큰. OAuth2 provider: "
                                                + Arrays.stream(OAuth2Provider.values()).toList()
                                        )
                        ),
                        responseFields(
                                fieldWithPath("userId").description("사용자 ID"),
                                fieldWithPath("accessToken").description("액세스 토큰"),
                                fieldWithPath("accessTokenIssuedAt").description("액세스 토큰 발급 시각"),
                                fieldWithPath("accessTokenExpiresAt").description("액세스 토큰 만료 시각"),
                                fieldWithPath("refreshToken").description("리프레시 토큰"),
                                fieldWithPath("refreshTokenIssuedAt").description("리프레시 토큰 발급 시각"),
                                fieldWithPath("refreshTokenExpiresAt").description("리프레시 토큰 만료 시각")
                        )
                ));
    }
}