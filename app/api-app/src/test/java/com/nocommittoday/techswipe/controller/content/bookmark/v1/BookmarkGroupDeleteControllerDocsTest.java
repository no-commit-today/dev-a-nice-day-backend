package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.BookmarkGroupDeleteController;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupDeleteService;
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

@WebMvcTest(BookmarkGroupDeleteController.class)
class BookmarkGroupDeleteControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private BookmarkGroupDeleteService bookmarkGroupDeleteService;

    @BeforeEach
    void setUp() {
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(AccessTokenDecodedBuilder.valid());
    }

    @Test
    void 북마크_그룹_삭제() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(delete("/api/bookmark/v1/groups/{groupName}", "프론트")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark/group-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        pathParameters(
                                parameterWithName("groupName").description("그룹 이름")
                        )
                ));
    }
}