package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.LogoutRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.test.RefreshTokenDecodedBuilder;
import com.nocommittoday.techswipe.domain.user.RefreshTokenDecoded;
import com.nocommittoday.techswipe.domain.user.UserLogoutService;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtRefreshTokenDecoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

@WebMvcTest(LogoutController.class)
class LogoutControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private UserLogoutService userLogoutService;

    @MockBean
    private JwtRefreshTokenDecoder jwtRefreshTokenDecoder;

    @Test
    void 로그아웃_Docs() throws Exception{
        // given
        LogoutRequest logoutRequest = new LogoutRequest("refresh-token");
        RefreshTokenDecoded refreshTokenDecoded = RefreshTokenDecodedBuilder.valid();
        given(jwtRefreshTokenDecoder.decode(logoutRequest.refreshToken()))
                .willReturn(refreshTokenDecoded);

        // when
        // then
        mockMvc.perform(post("/api/user/v1/logout")
                .content(objectMapper.writeValueAsString(logoutRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("user/logout",
                        requestFields(
                                fieldWithPath("refreshToken").description("리프레시 토큰")
                        )
                ));
    }
}