package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.controller.content.v1.request.UserCategorySaveRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.UserCategoryList;
import com.nocommittoday.techswipe.domain.content.UserCategoryService;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserCategoryController.class)
class UserCategoryControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private UserCategoryService userCategoryService;

    @Test
    void 유저_카테고리_조회() throws Exception {
        // given
        UserId userId = new UserId(1L);
        mockAccessTokenToUserId("access-token", userId);
        given(userCategoryService.get(new ApiUser(userId)))
                .willReturn(new UserCategoryList(userId, List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING)));

        // when
        // then
        mockMvc.perform(get("/api/content/v1/user/categories")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
        )
                .andExpect(status().isOk())
                .andDo(document("content/user-category-get",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        responseFields(
                                fieldWithPath("categories[]").description("유저 카테고리").type(TechCategory.class.getSimpleName())
                        )
                ));

    }

    @Test
    void 유저_카테고리_저장() throws Exception {
        // given
        UserId userId = new UserId(1L);
        mockAccessTokenToUserId("access-token", userId);
        UserCategorySaveRequest request = new UserCategorySaveRequest(List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING));

        // when
        // then
        mockMvc.perform(put("/api/content/v1/user/categories")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andDo(document("content/user-category-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        requestFields(
                                fieldWithPath("categories[]").description("유저 카테고리")
                                        .type(TechCategory.class.getSimpleName())
                        )
                ));
    }
}