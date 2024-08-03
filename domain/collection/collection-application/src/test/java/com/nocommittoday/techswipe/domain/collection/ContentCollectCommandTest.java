package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ContentCollectCommandTest {

    @Test
    void ContentCollect_도메인_객체를_생성할_수_있다() {
        // given
        final ContentCollectCommand contentCollectCommand = new ContentCollectCommand(
                new TechContentProviderId(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        final ContentCollect contentCollect = contentCollectCommand.toDomain(new CollectedContentId(2));

        // then
        assertThat(contentCollect.id()).isEqualTo(new CollectedContentId(2));
        assertThat(contentCollect.providerId()).isEqualTo(new TechContentProviderId(1));
        assertThat(contentCollect.url()).isEqualTo("url");
        assertThat(contentCollect.title()).isEqualTo("title");
        assertThat(contentCollect.publishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(contentCollect.content()).isEqualTo("content");
        assertThat(contentCollect.imageUrl()).isEqualTo("imageUrl");
    }
}