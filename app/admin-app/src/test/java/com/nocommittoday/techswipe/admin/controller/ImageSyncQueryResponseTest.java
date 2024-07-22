package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageSyncQueryResponseTest {

    @Test
    void 도메인_엔티티로부터_생성할_수_있다() {
        // given
        final Image image = new Image(
                new Image.Id(1L),
                "url",
                "originalUrl",
                "storedName"
        );

        // when
        final ImageSyncQueryResponse response = ImageSyncQueryResponse.from(image);

        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.url()).isEqualTo("url");
        assertThat(response.originalUrl()).isEqualTo("originalUrl");
        assertThat(response.storedName()).isEqualTo("storedName");
    }
}