package com.nocommittoday.techswipe.domain.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageUrlResultTest {

    @Test
    void from() {
        // given
        final Image image = new Image(
                new ImageId(1),
                "test-url",
                "test-origin-url",
                "test-stored-name"
        );

        // when
        final ImageUrlResult result = ImageUrlResult.from(image);

        // then
        assertThat(result.id()).isEqualTo(new ImageId(1));
        assertThat(result.url()).isEqualTo("test-url");
    }
}