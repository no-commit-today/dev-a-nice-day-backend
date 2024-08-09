package com.nocommittoday.techswipe.controller.user.v1;

import com.nocommittoday.techswipe.controller.user.v1.request.UserSignupRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.UserSignupService;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserSignupController.class)
class UserSignupControllerTest extends AbstractDocsTest {

    @MockBean
    private UserSignupService userSignUpService;

    @Test
    void 회원가입_Docs() throws Exception {
        // given
        UserSignupRequest request = new UserSignupRequest("github-access-token");
        given(userSignUpService.signUp(request.accessToken()))
                .willReturn(new UserId(1L));

        // when
        // then
        mockMvc.perform(post("/api/user/v1/signup")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("user/signup",
                        requestFields(
                                fieldWithPath("accessToken")
                                        .description("OAuth2 액세스 토큰. 회원가입 가능 OAuth2 provider: "
                                                + Arrays.stream(OAuth2Provider.values())
                                                .filter(OAuth2Provider::canSignUp).toList()
                                        )
                        )
                ));
    }
}