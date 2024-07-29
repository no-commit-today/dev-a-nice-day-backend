package com.nocommittoday.techswipe.docs.restdocs;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
@Tag("restdocs")
public abstract class AbstractDocsTest {
}
