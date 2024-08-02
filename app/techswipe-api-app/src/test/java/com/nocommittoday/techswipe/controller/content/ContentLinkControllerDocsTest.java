package com.nocommittoday.techswipe.controller.content;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.service.TechContentLinkService;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContentLinkController.class)
class ContentLinkControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private TechContentLinkService techContentLinkService;

    @Test
    void 컨텐츠_링크_Docs() throws Exception {
        // given
        given(techContentLinkService.link(new TechContentId(1L)))
                .willReturn("https://content-url");

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/contents/{contentId}/link", 1L))
                .andExpect(status().isFound())
                .andDo(document("content/content-link",
                        pathParameters(
                                parameterWithName("contentId").description("컨텐츠 ID")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("링크 URL")
                        ))
                );
    }
}