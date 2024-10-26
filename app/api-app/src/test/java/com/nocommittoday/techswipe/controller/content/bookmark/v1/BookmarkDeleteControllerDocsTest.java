package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.BookmarkDeleteController;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkDeleteService;
import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkDeleteController.class)
class BookmarkDeleteControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private BookmarkDeleteService bookmarkDeleteService;

    @BeforeEach
    void setUp() {
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(AccessTokenDecodedBuilder.valid());
    }

    @Test
    void 북마크_삭제() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(delete("/api/bookmark/v1/groups/{groupName}/contents/{contentId}", "프론트", 1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
        )
                .andExpect(status().isOk())
                .andDo(document("bookmark/delete",
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