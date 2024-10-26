package com.nocommittoday.techswipe.docs.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocommittoday.techswipe.domain.test.AccessTokenDecodedBuilder;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtAccessTokenDecoder;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
@Tag("restdocs")
public abstract class AbstractDocsTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JwtAccessTokenDecoder jwtAccessTokenDecoder;

    protected void mockAccessTokenToUserId(String accessToken, UserId userId) {
        given(jwtAccessTokenDecoder.decode(accessToken))
                .willReturn(AccessTokenDecodedBuilder.valid(userId));
    }
}
