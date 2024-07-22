package com.nocommittoday.techswipe.docs.restdocs;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
public abstract class AbstractDocsTest {
}
