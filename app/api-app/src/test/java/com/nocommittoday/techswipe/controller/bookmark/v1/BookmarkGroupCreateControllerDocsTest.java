package com.nocommittoday.techswipe.controller.bookmark.v1;

import com.nocommittoday.techswipe.controller.bookmark.v1.request.BookmarkGroupCreateRequest;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import com.nocommittoday.techswipe.docs.restdocs.RestDocsAttribute;
import com.nocommittoday.techswipe.domain.bookmark.BookmarkConst;
import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroupCreateService;
import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkGroupCreateController.class)
class BookmarkGroupCreateControllerDocsTest extends AbstractDocsTest {

    @MockBean
    private BookmarkGroupCreateService bookmarkGroupCreateService;

    @BeforeEach
    void setUp() {
        given(jwtAccessTokenDecoder.decode("access-token"))
                .willReturn(AccessTokenDecodedBuilder.valid());
    }

    @Test
    void 북마크_그룹_생성() throws Exception {
        // given
        BookmarkGroupCreateRequest request = new BookmarkGroupCreateRequest("프론트");

        // when
        // then
        mockMvc.perform(post("/api/bookmark/v1/groups")
                        .header("Authorization", "Bearer access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("bookmark/create-group",
                                requestHeaders(
                                        headerWithName("Authorization").description("인증 필요")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("그룹 이름").attributes(RestDocsAttribute.constraint(String.format("최대 %d자", BookmarkConst.MAX_GROUP_NAME_LENGTH)))
                                )
                        )
                )
        ;
    }

}