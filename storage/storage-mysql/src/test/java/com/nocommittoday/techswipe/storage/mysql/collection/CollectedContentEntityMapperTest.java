package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.collection.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CollectedContentEntityMapperTest {

    @InjectMocks
    private CollectedContentEntityMapper collectedContentEntityMapper;

    @Mock
    private TechContentProviderJpaRepository techContentProviderJpaRepository;

    @Test
    void 도메인_엔티티로부터_생성할_수_있다() {
        // given
        CollectedContent content = new CollectedContent(
                new CollectedContentId(1L),
                CollectionStatus.INIT,
                List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER),
                "summary",
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        TechContentProviderEntity techContentProviderEntity = mock(TechContentProviderEntity.class);
        given(techContentProviderJpaRepository.getReferenceById(2L)).willReturn(techContentProviderEntity);

        // when
        CollectedContentEntity entity = collectedContentEntityMapper.from(content);

        // then
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getStatus()).isEqualTo(CollectionStatus.INIT);
        assertThat(entity.getCategories()).isEqualTo(List.of(CollectionCategory.DEVOPS, CollectionCategory.SERVER));
        assertThat(entity.getSummary()).isEqualTo("summary");
        assertThat(entity.getProvider()).isEqualTo(techContentProviderEntity);
        assertThat(entity.getUrl()).isEqualTo("url");
        assertThat(entity.getTitle()).isEqualTo("title");
        assertThat(entity.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entity.getContent()).isEqualTo("content");
        assertThat(entity.getImageUrl()).isEqualTo("imageUrl");
    }

    @Test
    void ContentCollect_도메인_객체로부터_생성할_수_있다() {
        // given
        ContentCollect contentCollect = new ContentCollect(
                new CollectedContentId(1L),
                new TechContentProviderId(2L),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        TechContentProviderEntity techContentProviderEntity = mock(TechContentProviderEntity.class);
        given(techContentProviderJpaRepository.getReferenceById(2L)).willReturn(techContentProviderEntity);

        // when
        CollectedContentEntity entity = collectedContentEntityMapper.from(contentCollect);

        // then
        assertThat(entity.getId()).isEqualTo(1);
        assertThat(entity.getStatus()).isEqualTo(CollectionStatus.INIT);
        assertThat(entity.getCategories()).isNull();
        assertThat(entity.getSummary()).isNull();
        assertThat(entity.getProvider()).isEqualTo(techContentProviderEntity);
        assertThat(entity.getUrl()).isEqualTo("url");
        assertThat(entity.getTitle()).isEqualTo("title");
        assertThat(entity.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(entity.getContent()).isEqualTo("content");
        assertThat(entity.getImageUrl()).isEqualTo("imageUrl");
    }
}