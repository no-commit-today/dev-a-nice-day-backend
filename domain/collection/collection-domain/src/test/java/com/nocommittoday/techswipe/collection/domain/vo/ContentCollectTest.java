package com.nocommittoday.techswipe.collection.domain.vo;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.CollectionType;
import com.nocommittoday.techswipe.collection.domain.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ContentCollectTest {

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        ContentCollect contentCollect = new ContentCollect(
                CollectionType.FEED,
                new TechContentProvider.TechContentProviderId(1L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );

        // when
        final CollectedContent result = contentCollect.toDomain();

        // then
        assertThat(result.getId()).isNull();
        assertThat(result.getType()).isEqualTo(CollectionType.FEED);
        assertThat(result.getStatus()).isEqualTo(CollectionStatus.INIT);
        assertThat(result.getCategories()).isNull();
        assertThat(result.getSummary()).isNull();
        assertThat(result.getProviderId()).isEqualTo(new TechContentProvider.TechContentProviderId(1L));
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getImageUrl()).isEqualTo("imageUrl");
    }
}