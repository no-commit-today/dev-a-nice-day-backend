package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.test.SummaryBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentQueryTest {

    @Test
    void 게터() {
        // given
        Summary summary = SummaryBuilder.create();
        TechContentQuery techContentQuery = new TechContentQuery(
                new TechContentId(1),
                new TechContentProviderQuery(
                        new TechContentProviderId(2),
                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                        "title",
                        "url",
                        "iconUrl"
                ),
                "imageUrl",
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                summary,
                List.of(TechCategory.SERVER)
        );

        // when
        // then
        assertThat(techContentQuery.getId()).isEqualTo(new TechContentId(1));
        assertThat(techContentQuery.getImageUrl()).isEqualTo("imageUrl");
        assertThat(techContentQuery.getUrl()).isEqualTo("url");
        assertThat(techContentQuery.getTitle()).isEqualTo("title");
        assertThat(techContentQuery.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(techContentQuery.getSummary()).isEqualTo(summary);
        assertThat(techContentQuery.getCategories()).isEqualTo(List.of(TechCategory.SERVER));
    }
}