package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaQueryRepository;
import com.nocommittoday.techswipe.storage.mysql.test.TechContentEntityBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentSwipeQueryReaderTest {

    @InjectMocks
    private TechContentSwipeQueryReader techContentSwipeQueryReader;

    @Mock
    private TechContentJpaQueryRepository techContentJpaRepository;

    @Test
    void 스와이프_쿼리를_읽을_수_있다() {
        // given
        TechContentEntity techContentEntity = TechContentEntityBuilder.create();
        PageParam pageParam = new PageParam(1, 10);
        List<TechCategory> categories = List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING);
        given(techContentJpaRepository.findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
                pageParam, categories
        )).willReturn(List.of(techContentEntity));

        // when
        List<TechContentSwipeQueryResult> result = techContentSwipeQueryReader.getList(pageParam, categories);

        // then
        Assertions.assertThat(result).hasSize(1);
        assertThat(result.get(0).id().value()).isEqualTo(techContentEntity.getId());
        assertThat(result.get(0).url()).isEqualTo(techContentEntity.getUrl());
        assertThat(result.get(0).title()).isEqualTo(techContentEntity.getTitle());
        assertThat(result.get(0).publishedDate()).isEqualTo(techContentEntity.getPublishedDate());
        assertThat(result.get(0).imageUrl()).isNull();
        assertThat(result.get(0).summary()).isEqualTo(techContentEntity.getSummary());
        assertThat(result.get(0).categories()).isEqualTo(techContentEntity.getCategories());
        assertThat(result.get(0).providerId().value()).isEqualTo(techContentEntity.getProvider().getId());
        assertThat(result.get(0).providerTitle()).isEqualTo(techContentEntity.getProvider().getTitle());
        assertThat(result.get(0).providerUrl()).isEqualTo(techContentEntity.getProvider().getUrl());
        assertThat(result.get(0).providerIconUrl()).isNull();
    }

}