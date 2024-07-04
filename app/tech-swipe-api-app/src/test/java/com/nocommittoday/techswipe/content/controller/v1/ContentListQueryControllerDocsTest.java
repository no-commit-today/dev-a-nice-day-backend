package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.service.ContentListQueryParam;
import com.nocommittoday.techswipe.content.service.ContentListQueryService;
import com.nocommittoday.techswipe.content.service.ContentQueryResult;
import com.nocommittoday.techswipe.content.service.TechContentProviderQueryResult;
import com.nocommittoday.techswipe.core.controller.servlet.PageRequest;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ContentListQueryController.class)
class ContentListQueryControllerDocsTest extends AbstractDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContentListQueryService contentListQueryService;


    @Test
    void 컨텐츠_리스트_조회_Docs() throws Exception {
        // given
        given(contentListQueryService.getList(
                new PageParam(1, 10),
                new ContentListQueryParam(List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING))
        )).willReturn(List.of(
                new ContentQueryResult(
                        new TechContent.Id(1L),
                        new TechContentProviderQueryResult(
                                new TechContentProvider.Id(2L),
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
                .andDo(print())
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
                                fieldWithPath("content[].imageUrl").description("컨텐츠 이미지 URL"),
                                fieldWithPath("content[].categories").description("카테고리 목록")
                                        .attributes(RestDocsAttribute.type(TechCategory.class)),
                                fieldWithPath("content[].providerId").description("제공자 ID"),
                                fieldWithPath("content[].providerTitle").description("제공자 제목"),
                                fieldWithPath("content[].providerUrl").description("제공자 URL"),
                                fieldWithPath("content[].providerIconUrl").description("제공자 아이콘 URL")
                        )
                ));
    }

    @Test
    void 컨텐츠_개수_조회_Docs() throws Exception {
        // given
        given(
                contentListQueryService.count(
                        new ContentListQueryParam(
                                List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING)))
        ).willReturn(100L);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/v1/contents-count")
                        .param("categories", TechCategory.SERVER.name())
                        .param("categories", TechCategory.SW_ENGINEERING.name())
                )
                .andDo(print())
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
