package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.content.TechContentDetailQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentDetailQueryService;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TechContentDetailQueryController.class)
class TechContentDetailQueryControllerTest extends AbstractDocsTest {

    @MockBean
    private TechContentDetailQueryService techContentDetailQueryService;

    @Test
    void 컨텐츠_상세조회_Docs() throws Exception {
        // given
        given(techContentDetailQueryService.get(new TechContentId(1)))
                .willReturn(new TechContentDetailQueryResult(
                                new TechContentId(1L),
                                "url",
                                "title",
                                LocalDate.of(2021, 1, 1),
                                "content-image-url",
                                "1. 요약-1\n2. 요약-2\n3. 요약-3",
                                List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING),
                                new TechContentProviderId(2L),
                                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                                "provider-title",
                                "provider-url",
                                "provider-icon-url"
                        )
                );
        // when
        mockMvc.perform(get("/api/content/v1/contents/{contentId}", 1L))
                .andExpect(status().isOk())
                .andDo(document("content/get-content-detail",
                        pathParameters(
                                parameterWithName("contentId").description("컨텐츠 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("컨텐츠 ID"),
                                fieldWithPath("url").description("컨텐츠 URL"),
                                fieldWithPath("title").description("컨텐츠 제목"),
                                fieldWithPath("publishedDate").description("컨텐츠 발행일"),
                                fieldWithPath("imageUrl").description("컨텐츠 이미지 URL").optional(),
                                fieldWithPath("summary").description("컨텐츠 요약"),
                                fieldWithPath("categories").description("컨텐츠 카테고리들(리스트)"),
                                fieldWithPath("providerId").description("컨텐츠 제공자 ID"),
                                fieldWithPath("providerType").description("컨텐츠 제공자 타입"),
                                fieldWithPath("providerTitle").description("컨텐츠 제공자 제목"),
                                fieldWithPath("providerUrl").description("컨텐츠 제공자 URL"),
                                fieldWithPath("providerIconUrl").description("컨텐츠 제공자 아이콘 URL").optional()
                        )
                ));
    }
}