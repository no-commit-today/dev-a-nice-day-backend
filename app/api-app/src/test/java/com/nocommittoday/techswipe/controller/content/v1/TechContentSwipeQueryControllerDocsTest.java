package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.controller.core.PageRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentSwipeQueryParam;
import com.nocommittoday.techswipe.domain.content.TechContentSwipeQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentSwipeQueryService;
import com.nocommittoday.techswipe.domain.core.PageParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TechContentSwipeQueryController.class)
class TechContentSwipeQueryControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private TechContentSwipeQueryService techContentSwipeQueryService;

    @Test
    void 컨텐츠_스와이프_리스트_조회_Docs() throws Exception {
        // given
        given(techContentSwipeQueryService.getList(
                new PageParam(1, 10),
                new TechContentSwipeQueryParam(List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING))
        )).willReturn(List.of(
                new TechContentSwipeQueryResult(
                        new TechContentId(1L),
                        "content-url",
                        "content-title",
                        LocalDate.of(2021, 1, 1),
                        "content-image-url",
                        "content-summary",
                        List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING),
                        new TechContentProviderId(2L),
                        "provider-title",
                        "https://provider-url",
                        "https://provider-icon-url"
                )
        ));

        // when
        mockMvc.perform(get("/api/content/v1/swipes")
                        .param("page", "1")
                        .param("size", "10")
                        .param("categories", "SERVER", "SW_ENGINEERING")
                )
                .andExpect(status().isOk())
                .andDo(document("content/get-content-swipe-list",
                        queryParameters(
                                parameterWithName("page").description("페이지 번호")
                                        .attributes(RestDocsAttribute.defaultValue(PageRequest.DEFAULT_PAGE))
                                        .optional(),
                                parameterWithName("size").description("페이지 크기")
                                        .attributes(RestDocsAttribute.defaultValue(PageRequest.DEFAULT_SIZE))
                                        .optional(),
                                parameterWithName("categories").description("카테고리 목록. 여러 개 전달 가능.")
                                        .attributes(RestDocsAttribute.type(TechCategory.class))
                                        .attributes(RestDocsAttribute.defaultValue("모든 카테고리"))
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("content").description("리스트 데이터"),
                                fieldWithPath("content[].id").description("컨텐츠 ID"),
                                fieldWithPath("content[].url").description("컨텐츠 URL"),
                                fieldWithPath("content[].title").description("컨텐츠 제목"),
                                fieldWithPath("content[].publishedDate").description("컨텐츠 발행일"),
                                fieldWithPath("content[].imageUrl").description("컨텐츠 이미지 URL").optional(),
                                fieldWithPath("content[].summary").description("컨텐츠 요약"),
                                fieldWithPath("content[].categories").description("카테고리 목록")
                                        .attributes(RestDocsAttribute.type(TechCategory.class)),
                                fieldWithPath("content[].providerId").description("제공자 ID"),
                                fieldWithPath("content[].providerTitle").description("제공자 제목"),
                                fieldWithPath("content[].providerUrl").description("제공자 URL"),
                                fieldWithPath("content[].providerIconUrl").description("제공자 아이콘 URL").optional()
                        )
                ));
    }
}