package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.test.SummaryBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentTest {

    @Test
    void 게터_세터() {
        // given
        Summary summary = SummaryBuilder.create();
        TechContent techContent = new TechContent(
                new TechContentId(1),
                new TechContentProviderId(2),
                new ImageId(3),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                summary,
                List.of(TechCategory.SERVER)
        );

        // when
        // then
        assertThat(techContent.getId().value()).isEqualTo(1);
        assertThat(techContent.getProviderId().value()).isEqualTo(2);
        assertThat(techContent.getImageId().value()).isEqualTo(3);
        assertThat(techContent.getUrl()).isEqualTo("url");
        assertThat(techContent.getTitle()).isEqualTo("title");
        assertThat(techContent.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(techContent.getSummary()).isEqualTo(summary);
        assertThat(techContent.getCategories()).containsExactly(TechCategory.SERVER);
    }
}