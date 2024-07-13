package com.nocommittoday.techswipe.image.infrastructure.dev;

import com.nocommittoday.techswipe.image.infrastructure.ContentTypeReader;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("develop")
@SpringBootTest
class ContentTypeReaderDevTest {

    @Autowired
    private ContentTypeReader contentTypeReader;

    @Test
    void 우아한형제들_아이콘() {
        // given
        // when
        final String contentType = contentTypeReader.getContentType(
                "https://techblog.woowahan.com/wp-content/uploads/2020/08/favicon.ico");

        // then
        assertThat(contentType).isEqualTo("image/x-icon");
    }
}