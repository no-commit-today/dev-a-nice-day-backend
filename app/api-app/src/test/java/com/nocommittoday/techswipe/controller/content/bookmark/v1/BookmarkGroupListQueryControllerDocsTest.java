package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroup;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupId;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupListQueryService;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupWithContains;
import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkGroupListQueryController.class)
class BookmarkGroupListQueryControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private BookmarkGroupListQueryService bookmarkGroupListQueryService;

    @BeforeEach
    void setUp() {
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(AccessTokenDecodedBuilder.valid());
    }

    @Test
    void 북마크_그룹_리스트_조회_Docs() throws Exception {
        // given
        given(bookmarkGroupListQueryService.getList(any())).willReturn(
                List.of(new BookmarkGroup(
                        new BookmarkGroupId(1L),
                        new UserId(1L),
                        "프론트"
                ))
        );

        // when
        // then
        mockMvc.perform(get("/api/bookmark/v1/groups")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token"))
                .andExpect(status().isOk())
                .andDo(document("bookmark/groups",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        responseFields(
                                fieldWithPath("content").description("리스트 데이터"),
                                fieldWithPath("content[].name").description("그룹 이름")
                        )
                ));
    }

    @Test
    void 북마크_여부를_포함한_북마크_그룹_리스트_조회_Docs() throws Exception {
        // given
        given(bookmarkGroupListQueryService.getListWithContains(any(), any())).willReturn(
                List.of(new BookmarkGroupWithContains(
                                new BookmarkGroup(
                                        new BookmarkGroupId(1L),
                                        new UserId(1L),
                                        "프론트"
                                ),
                        true
                        )
                )
        );

        // when
        // then
        mockMvc.perform(get("/api/bookmark/v1/groups-with-contains")
                        .queryParam("contentId", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token"))
                .andExpect(status().isOk())
                .andDo(document("bookmark/get-groups-with-contains",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        queryParameters(
                                parameterWithName("contentId").description("컨텐츠 ID")
                        ),
                        responseFields(
                                fieldWithPath("content").description("리스트 데이터"),
                                fieldWithPath("content[].name").description("그룹 이름"),
                                fieldWithPath("content[].contains").description("북마크 포함 여부")
                        )
                ));
    }
}