package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryParam;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryService;
import com.nocommittoday.techswipe.domain.content.provider.TechContentProviderQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentQueryResult;
import com.nocommittoday.techswipe.controller.core.PageRequest;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TechContentListQueryController.class)
class TechContentListQueryControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private TechContentListQueryService techContentListQueryService;


    @Test
    void 컨텐츠_리스트_조회_Docs() throws Exception {
        // given
        given(techContentListQueryService.getList(
                new PageParam(1, 10),
                new TechContentListQueryParam(List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING))
        )).willReturn(List.of(
                new TechContentQueryResult(
                        new TechContentId(1L),
                        new TechContentProviderQueryResult(
                                new TechContentProviderId(2L),
                                "title",
                                "https://provider-url",
                                "https://provider-icon-url"
                        ),
                        "https://content-url",
                        "title",
                        LocalDate.of(2021, 1, 1),
                        "https://content-image-url",
                        "summary",
                        List.of(TechCategory.SERVER)
                )
        ));

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/v1/contents")
                        .param("page", "1")
                        .param("size", "10")
                        .param("categories", TechCategory.SERVER.name())
                        .param("categories", TechCategory.SW_ENGINEERING.name())
                )
                .andExpect(status().isOk())
                .andDo(document("content/get-content-list",
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
                                fieldWithPath("content[].title").description("컨텐츠 제목"),
                                fieldWithPath("content[].publishedDate").description("컨텐츠 발행일"),
                                fieldWithPath("content[].summary").description("컨텐츠 요약"),
                                fieldWithPath("content[].imageUrl").description("컨텐츠 이미지 URL").optional(),
                                fieldWithPath("content[].categories").description("카테고리 목록")
                                        .attributes(RestDocsAttribute.type(TechCategory.class)),
                                fieldWithPath("content[].providerId").description("제공자 ID"),
                                fieldWithPath("content[].providerTitle").description("제공자 제목"),
                                fieldWithPath("content[].providerUrl").description("제공자 URL"),
                                fieldWithPath("content[].providerIconUrl").description("제공자 아이콘 URL").optional()
                        )
                ));
    }

    @Test
    void 컨텐츠_개수_조회_Docs() throws Exception {
        // given
        given(
                techContentListQueryService.count(
                        new TechContentListQueryParam(
                                List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING)))
        ).willReturn(100L);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/v1/contents-count")
                        .param("categories", TechCategory.SERVER.name())
                        .param("categories", TechCategory.SW_ENGINEERING.name())
                )
                .andExpect(status().isOk())
                .andDo(document("content/get-content-count",
                        queryParameters(
                                parameterWithName("categories").description("카테고리 목록. 여러 개 전달 가능.")
                                        .attributes(RestDocsAttribute.type(TechCategory.class))
                                        .attributes(RestDocsAttribute.defaultValue("모든 카테고리"))
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("count").description("컨텐츠 개수")
                        )
                ));
    }

}
