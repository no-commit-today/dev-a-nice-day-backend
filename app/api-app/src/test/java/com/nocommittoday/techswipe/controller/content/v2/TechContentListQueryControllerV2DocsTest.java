package com.nocommittoday.techswipe.controller.content.v2;

import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryParamNew;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryServiceNew;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderQuery;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.content.TechContentQuery;
import com.nocommittoday.techswipe.domain.content.TechContentQueryResult;
import com.nocommittoday.techswipe.domain.test.SummaryBuilder;
import com.nocommittoday.techswipe.domain.user.ApiUserOrGuest;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TechContentListQueryControllerV2.class)
class TechContentListQueryControllerV2DocsTest extends AbstractDocsTest {

    @MockBean
    private TechContentListQueryServiceNew techContentListQueryService;

    @Test
    void 컨텐츠_리스트_조회_V2_Docs() throws Exception {
        // given
        UserId userId = new UserId(123L);
        mockAccessTokenToUserId("access-token", userId);
        given(techContentListQueryService.getList(
                ApiUserOrGuest.user(userId),
                new TechContentListQueryParamNew(
                        new TechContentId(1234L),
                        List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING),
                        10
                )
        )).willReturn(new TechContentListQueryResult(List.of(
                new TechContentQueryResult(
                        new TechContentQuery(
                                new TechContentId(2345L),
                                new TechContentProviderQuery(
                                        new TechContentProviderId(2L),
                                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                                        "title",
                                        "https://provider-url",
                                        "https://provider-icon-url"
                                ),
                                "image-url",
                                "https://content-url",
                                "title",
                                LocalDate.of(2021, 1, 1),
                                SummaryBuilder.create(),
                                List.of(TechCategory.SERVER)
                        ),
                        true
                )
        )));

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/v2/contents")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .param("id", "1234")
                        .param("size", "10")
                        .param("categories", TechCategory.SERVER.name())
                        .param("categories", TechCategory.SW_ENGINEERING.name())
                )
                .andExpect(status().isOk())
                .andDo(document("content/get-content-list-v2",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰. 없을 경우 게스트.").optional()
                        ),
                        queryParameters(
                                parameterWithName("id").description("지난 페이지 목록의 마지막 아이디. null 일 경우 첫 페이지.")
                                        .optional(),
                                parameterWithName("size").description("페이지 크기")
                                        .attributes(RestDocsAttribute.defaultValue(TechContentListQueryParamNew.DEFAULT_SIZE))
                                        .optional(),
                                parameterWithName("categories").description("카테고리 목록. 여러 개 전달 가능.")
                                        .attributes(RestDocsAttribute.type(TechCategory.class))
                                        .attributes(RestDocsAttribute.defaultValue("모든 카테고리"))
                                        .optional()
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
                                fieldWithPath("content[].bookmarked").description("북마크 여부")
                        )
                ));
    }
}