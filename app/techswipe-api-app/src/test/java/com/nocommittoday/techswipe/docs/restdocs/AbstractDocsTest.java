package com.nocommittoday.techswipe.docs.restdocs;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
@Tag("restdocs")
public abstract class AbstractDocsTest {

    @Autowired
    protected MockMvc mockMvc;
}
