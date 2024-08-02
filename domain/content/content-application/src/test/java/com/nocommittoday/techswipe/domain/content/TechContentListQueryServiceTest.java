package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.core.domain.PageParam;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.infrastructure.ImageReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentListQueryServiceTest {

    @InjectMocks
    private TechContentListQueryService techContentListQueryService;

    @Mock
    private TechContentCategorizedListReader techContentCategorizedListReader;

    @Mock
    private ImageReader imageReader;

    @Test
    void 컨텐츠_리스트를_조회할_수_있다() {
        // given
        PageParam pageParam = new PageParam(1, 10);
        TechContentProvider contentProvider = new TechContentProvider(
                new TechContentProviderId(11L),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                new ImageId(21L)
        );
        given(techContentCategorizedListReader.getList(pageParam, List.of(TechCategory.SERVER)))
                .willReturn(List.of(
                        new TechContent(
                                new TechContentId(1L),
                                contentProvider,
                                null,
                                "url-1",
                                "title-1",
                                LocalDate.of(2021, 1, 1),
                                "summary-1",
                                List.of(TechCategory.SERVER)
                        ),
                        new TechContent(
                                new TechContentId(2L),
                                contentProvider,
                                new ImageId(22L),
                                "url-2",
                                "title-2",
                                LocalDate.of(2021, 1, 1),
                                "summary-2",
                                List.of(TechCategory.SERVER)
                        )
                ));
        given(imageReader.getAll(Set.of(new ImageId(21L), new ImageId(22L))))
                .willReturn(List.of(
                        new Image(new ImageId(21L), "url-21", "original-url-21", "stored-name-21")
                ));

        // when
        List<TechContentQueryResult> techContentQueryResults = techContentListQueryService.getList(pageParam, new TechContentListQueryParam(List.of(TechCategory.SERVER)));

        // then
        assertThat(techContentQueryResults).hasSize(2);
        assertThat(techContentQueryResults.get(0).id()).isEqualTo(new TechContentId(1L));
        assertThat(techContentQueryResults.get(0).provider().id()).isEqualTo(new TechContentProviderId(11L));
        assertThat(techContentQueryResults.get(0).provider().title()).isEqualTo("provider-title");
        assertThat(techContentQueryResults.get(0).provider().url()).isEqualTo("provider-url");
        assertThat(techContentQueryResults.get(0).provider().iconUrl())
                .describedAs("이미지가 있을 경우 이미지 url 을 반환한다.")
                .isEqualTo("url-21");
        assertThat(techContentQueryResults.get(0).url()).isEqualTo("url-1");
        assertThat(techContentQueryResults.get(0).title()).isEqualTo("title-1");
        assertThat(techContentQueryResults.get(0).publishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(techContentQueryResults.get(0).imageUrl())
                .describedAs("이미지 ID 가 없을 경우 null 을 반환한다.")
                .isNull();
        assertThat(techContentQueryResults.get(0).summary()).isEqualTo("summary-1");
        assertThat(techContentQueryResults.get(0).categories()).containsExactly(TechCategory.SERVER);
        assertThat(techContentQueryResults.get(1).id()).isEqualTo(new TechContentId(2L));
        assertThat(techContentQueryResults.get(1).provider().id()).isEqualTo(new TechContentProviderId(11L));
        assertThat(techContentQueryResults.get(1).provider().title()).isEqualTo("provider-title");
        assertThat(techContentQueryResults.get(1).publishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(techContentQueryResults.get(1).provider().url()).isEqualTo("provider-url");
        assertThat(techContentQueryResults.get(1).provider().iconUrl())
                .describedAs("이미지가 있을 경우 이미지 url 을 반환한다.")
                .isEqualTo("url-21");
        assertThat(techContentQueryResults.get(1).url()).isEqualTo("url-2");
        assertThat(techContentQueryResults.get(1).title()).isEqualTo("title-2");
        assertThat(techContentQueryResults.get(1).imageUrl())
                .describedAs("이미지 ID 가 있더라도 이미지가 없을 경우 null 을 반환한다.")
                .isNull();
        assertThat(techContentQueryResults.get(1).summary()).isEqualTo("summary-2");
        assertThat(techContentQueryResults.get(1).categories()).containsExactly(TechCategory.SERVER);
    }

}