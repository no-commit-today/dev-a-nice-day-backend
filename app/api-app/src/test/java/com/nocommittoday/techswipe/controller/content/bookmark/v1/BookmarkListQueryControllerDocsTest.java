package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderQuery;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.content.TechContentQuery;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroup;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupId;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkId;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkListQueryResult;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkListQueryService;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkQuery;
import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import com.nocommittoday.techswipe.domain.test.SummaryBuilder;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
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

@WebMvcTest(BookmarkListQueryController.class)
class BookmarkListQueryControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private BookmarkListQueryService bookmarkListQueryService;

    @BeforeEach
    void setUp() {
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(AccessTokenDecodedBuilder.valid());
    }

    @Test
    void 북마크_리스트_조회() throws Exception {
        // given
        given(bookmarkListQueryService.getList(any(), any())).willReturn(
                new BookmarkListQueryResult(
                        List.of(
                                new BookmarkQuery(
                                        new BookmarkId(1L),
                                        new BookmarkGroup(
                                                new BookmarkGroupId(1L),
                                                new UserId(2L),
                                                "프론트"
                                        ),
                                        new TechContentQuery(
                                                new TechContentId(3L),
                                                new TechContentProviderQuery(
                                                        new TechContentProviderId(4L),
                                                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                                                        "네이버",
                                                        "provider-url",
                                                        "provider-logo-url"
                                                ),
                                                "image-url",
                                                "url",
                                                "제목",
                                                LocalDate.of(2021, 1, 1),
                                                SummaryBuilder.create(),
                                                List.of(TechCategory.SERVER)
                                        )
                                )
                        )
                )
        );

        // when
        // then
        mockMvc.perform(get("/api/bookmark/v1/bookmarks")
                        .param("groupName", "프론트")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark/list-query",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        queryParameters(
                                parameterWithName("groupName").description("그룹 이름")
                                        .optional()
                                        .attributes(RestDocsAttribute.defaultValue("전체 그룹"))
                        ),
                        responseFields(
                                fieldWithPath("content").description("리스트 데이터"),
                                fieldWithPath("content[].id").description("컨텐츠 ID"),
                                fieldWithPath("content[].title").description("컨텐츠 제목"),
                                fieldWithPath("content[].publishedDate").description("컨텐츠 발행일"),
                                fieldWithPath("content[].summary").description("컨텐츠 요약"),
                                fieldWithPath("content[].imageUrl").description("컨텐츠 이미지 URL").optional(),
                                fieldWithPath("content[].categories").description("카테고리 목록")
                                        .attributes(RestDocsAttribute.type(TechCategory.class)),
                                fieldWithPath("content[].providerId").description("제공자 ID"),
                                fieldWithPath("content[].providerTitle").description("제공자 제목"),
                                fieldWithPath("content[].providerUrl").description("제공자 URL"),
                                fieldWithPath("content[].providerIconUrl").description("제공자 아이콘 URL").optional(),
                                fieldWithPath("content[].bookmarkGroupName").description("북마크 그룹 이름")
                        )
                ));
    }

    @Test
    void 북마크_그룹_최신_북마크_조회() throws Exception {
        // given
        given(bookmarkListQueryService.getGroupLatestList(any())).willReturn(
                List.of(
                        new BookmarkQuery(
                                new BookmarkId(1L),
                                new BookmarkGroup(
                                        new BookmarkGroupId(1L),
                                        new UserId(2L),
                                        "프론트"
                                ),
                                new TechContentQuery(
                                        new TechContentId(3L),
                                        new TechContentProviderQuery(
                                                new TechContentProviderId(4L),
                                                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                                                "네이버",
                                                "provider-url",
                                                "provider-logo-url"
                                        ),
                                        "image-url",
                                        "url",
                                        "제목",
                                        LocalDate.of(2021, 1, 1),
                                        SummaryBuilder.create(),
                                        List.of(TechCategory.SERVER)
                                )
                        )
                ));

        // when
        // then
        mockMvc.perform(get("/api/bookmark/v1/group-latest-bookmarks")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("bookmark/group-latest",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 필요")
                        ),
                        responseFields(
                                fieldWithPath("content").description("리스트 데이터"),
                                fieldWithPath("content[].id").description("컨텐츠 ID"),
                                fieldWithPath("content[].title").description("컨텐츠 제목"),
                                fieldWithPath("content[].publishedDate").description("컨텐츠 발행일"),
                                fieldWithPath("content[].summary").description("컨텐츠 요약"),
                                fieldWithPath("content[].imageUrl").description("컨텐츠 이미지 URL").optional(),
                                fieldWithPath("content[].categories").description("카테고리 목록")
                                        .attributes(RestDocsAttribute.type(TechCategory.class)),
                                fieldWithPath("content[].providerId").description("제공자 ID"),
                                fieldWithPath("content[].providerTitle").description("제공자 제목"),
                                fieldWithPath("content[].providerUrl").description("제공자 URL"),
                                fieldWithPath("content[].providerIconUrl").description("제공자 아이콘 URL").optional(),
                                fieldWithPath("content[].bookmarkGroupName").description("북마크 그룹 이름")
                        )
                ));
    }
}