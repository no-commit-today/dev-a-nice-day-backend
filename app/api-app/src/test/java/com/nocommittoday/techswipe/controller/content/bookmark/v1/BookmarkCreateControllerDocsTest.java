package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.request.BookmarkCreateRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkCreateService;
import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkCreateController.class)
class BookmarkCreateControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private BookmarkCreateService bookmarkCreateService;

    @BeforeEach
    void setUp() {
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(AccessTokenDecodedBuilder.valid());
    }

    @Test
    void 북마크_생성_Old_Docs() throws Exception {
        // given
        BookmarkCreateRequest request = new BookmarkCreateRequest(
                "프론트",
                1L
        );

        // when
        // then
        mockMvc.perform(post("/api/bookmark/v1/bookmarks")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("bookmark/create-old",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        requestFields(
                                fieldWithPath("groupName").description("그룹 이름"),
                                fieldWithPath("contentId").description("컨텐츠 ID")
                        )
                ));
    }

    @Test
    void 북마크_생성() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(put("/api/bookmark/v1/groups/{groupName}/contents/{contentId}", "프론트", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token"))
                .andExpect(status().isOk())
                .andDo(document("bookmark/create",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        pathParameters(
                                parameterWithName("groupName").description("그룹 이름"),
                                parameterWithName("contentId").description("컨텐츠 ID")
                        )
                ));
    }
}