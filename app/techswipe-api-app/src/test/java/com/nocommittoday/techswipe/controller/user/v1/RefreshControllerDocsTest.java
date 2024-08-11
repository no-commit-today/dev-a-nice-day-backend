package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.RefreshRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.test.AccessTokenBuilder;
import com.nocommittoday.techswipe.domain.test.RefreshTokenBuilder;
import com.nocommittoday.techswipe.domain.test.RefreshTokenDecodedBuilder;
import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.LoginResult;
import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.RefreshTokenDecoded;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.UserRefreshService;
import com.nocommittoday.techswipe.infrastructure.user.JwtAccessTokenEncoder;
import com.nocommittoday.techswipe.infrastructure.user.JwtRefreshTokenDecoder;
import com.nocommittoday.techswipe.infrastructure.user.JwtRefreshTokenEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RefreshController.class)
class RefreshControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private UserRefreshService userRefreshService;

    @MockBean
    private JwtRefreshTokenDecoder jwtRefreshTokenDecoder;

    @MockBean
    private JwtAccessTokenEncoder jwtAccessTokenEncoder;

    @MockBean
    private JwtRefreshTokenEncoder jwtRefreshTokenEncoder;

    @Test
    void 리프레시_Docs() throws Exception {
        // given
        RefreshRequest refreshRequest = new RefreshRequest("refresh-token");
        RefreshTokenDecoded refreshTokenDecoded = RefreshTokenDecodedBuilder.valid();
        UserId userId = new UserId(1L);
        AccessToken accessToken = new AccessTokenBuilder().userId(userId).build();
        RefreshToken refreshToken = new RefreshTokenBuilder().userId(userId).build();
        given(jwtRefreshTokenDecoder.decode(refreshRequest.refreshToken()))
                .willReturn(refreshTokenDecoded);
        given(userRefreshService.refresh(any(), any(), any())
        ).willReturn(new LoginResult(userId, accessToken, refreshToken));

        // when
        // then
        mockMvc.perform(post("/api/user/v1/refresh")
                        .content(objectMapper.writeValueAsString(refreshRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("user/refresh",
                        requestFields(
                                fieldWithPath("refreshToken").description("리프레시 토큰")
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