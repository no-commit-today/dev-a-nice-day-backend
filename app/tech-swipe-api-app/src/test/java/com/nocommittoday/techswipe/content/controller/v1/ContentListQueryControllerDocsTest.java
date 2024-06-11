package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.application.port.in.ContentListQuery;
import com.nocommittoday.techswipe.content.application.port.in.ContentListQueryParam;
import com.nocommittoday.techswipe.content.application.port.in.ContentResult;
import com.nocommittoday.techswipe.content.application.port.in.ProviderResult;
import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import com.nocommittoday.techswipe.image.application.port.in.ImageUrlQuery;
import com.nocommittoday.techswipe.image.application.port.in.ImageUrlResult;
import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    private ContentListQuery contentListQuery;

    @MockBean
    private ImageUrlQuery imageUrlQuery;

    @Test
    void 컨텐츠_리스트_조회_Docs() throws Exception {
        // given
        given(contentListQuery.getList(
                new PageParam(1, 10), new ContentListQueryParam(List.of(TechCategory.SERVER))
        )).willReturn(List.of(
                new ContentResult(
                        new TechContent.TechContentId(1L),
                        new ProviderResult(
                                new TechContentProvider.TechContentProviderId(2L),
                                "title",
                                "https://provider-url",
                                new Image.ImageId(3L)
                        ),
                        "https://content-url",
                        "title",
                        new Image.ImageId(4L),
                        "summary",
                        List.of(TechCategory.SERVER)
                )
        ));
        given(imageUrlQuery.getAll(List.of(new Image.ImageId(4L), new Image.ImageId(3L)))).willReturn(List.of(
                new ImageUrlResult(new Image.ImageId(4L), "https://content-image-url"),
                new ImageUrlResult(new Image.ImageId(3L), "https://provider-icon-url")
        ));

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/content/v1/contents")
                        .param("page", "1")
                        .param("size", "10")
                        .param("categories", TechCategory.SERVER.name())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("content/get-content-list",
                        queryParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기"),
                                parameterWithName("categories").description("카테고리 목록")
                        ),
                        responseFields(
                                fieldWithPath("content").description("리스트 데이터"),
                                fieldWithPath("content[].id").description("컨텐츠 ID"),
                                fieldWithPath("content[].url").description("컨텐츠 URL"),
                                fieldWithPath("content[].title").description("컨텐츠 제목"),
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

}
