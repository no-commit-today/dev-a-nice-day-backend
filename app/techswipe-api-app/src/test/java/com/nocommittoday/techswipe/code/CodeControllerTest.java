package com.nocommittoday.techswipe.code;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.core.domain.EnumMapperFactory;
import com.nocommittoday.techswipe.core.domain.EnumMapperType;
import com.nocommittoday.techswipe.docs.restdocs.AbstractDocsTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(EnumMapperConfig.class)
@WebMvcTest(controllers = CodeController.class)
class CodeControllerTest extends AbstractDocsTest {

    private static final String CODE_TYPES_PARAM = "codeTypes";

    @Autowired
    private EnumMapperFactory enumMapperFactory;

    @Test
    void 특정_코드_조회() throws Exception {
        // given
        enumMapperFactory.put(CodeEnum.class.getSimpleName(), CodeEnum.class);

        // expected
        mockMvc.perform(get("/api/code/v1/codes")
                        .accept(MediaType.APPLICATION_JSON)
                        .param(CODE_TYPES_PARAM, CodeEnum.class.getSimpleName()))
                .andExpect(status().isOk())
                .andDo(document("code/get-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("codeTypes").description("조회하려는 코드 타입들(리스트)")
                        ),
                        responseFields(
                                fieldWithPath("CodeEnum").description("코드 타입"),
                                fieldWithPath("CodeEnum[].code").description("코드"),
                                fieldWithPath("CodeEnum[].title").description("코드 제목")
                        )
                ));
    }

    @Test
    void 코드_전체_조회() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/code/v1/codes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.EnumCodeExample").isNotEmpty())  // 등록된 EnumMapperType
                .andExpect(jsonPath("$." + TechCategory.class.getSimpleName()).isNotEmpty())
                .andDo(document("code/get-all",
                        responseFields(
                                //subsectionWithPath("FieldCategory").description("분야 카테고리")    // RestDocs 등록
                                subsectionWithPath(TechCategory.class.getSimpleName()).description("기술 컨텐츠 카테고리")
                        )
                ));
    }

    enum CodeEnum implements EnumMapperType {
        CODE("코드 이름")
        ;

        private final String title;

        CodeEnum(String title) {
            this.title = title;
        }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getTitle() {
            return title;
        }
    }
}