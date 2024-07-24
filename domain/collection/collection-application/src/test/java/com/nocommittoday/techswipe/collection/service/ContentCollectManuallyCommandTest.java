package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ContentCollectManuallyCommandTest {

    @Test
    void ContentCollect_도메인_객체를_생성할_수_있다() {
        // given
        final ContentCollectManuallyCommand contentCollectManuallyCommand = new ContentCollectManuallyCommand(
                new TechContentProvider.Id(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        final ContentCollect contentCollect = contentCollectManuallyCommand.toDomain(new CollectedContentId(2));

        // then
        assertThat(contentCollect.id()).isEqualTo(new CollectedContentId(2));
        assertThat(contentCollect.providerId()).isEqualTo(new TechContentProvider.Id(1));
        assertThat(contentCollect.url()).isEqualTo("url");
        assertThat(contentCollect.title()).isEqualTo("title");
        assertThat(contentCollect.publishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(contentCollect.content()).isEqualTo("content");
        assertThat(contentCollect.imageUrl()).isEqualTo("imageUrl");
    }
}