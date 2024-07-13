package com.nocommittoday.techswipe.image.infrastructure.dev;

import com.nocommittoday.techswipe.image.infrastructure.ImageData;
import com.nocommittoday.techswipe.image.infrastructure.UrlImageReader;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("develop")
@SpringBootTest
class UrlImageReaderDevTest {

    @Autowired
    private UrlImageReader urlImageReader;

    @Test
    void 우아한형제들_아이콘() {
        final ImageData imageData = urlImageReader.get("https://techblog.woowahan.com/wp-content/uploads/2020/08/favicon.ico");
        System.out.println(imageData.contentLength());
        System.out.println(imageData.contentType());
    }
}