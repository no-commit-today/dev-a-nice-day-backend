package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.storage.mysql.test.AbstractDataJpaTest;
import com.nocommittoday.techswipe.storage.mysql.test.TechContentEntityBuilder;
import com.nocommittoday.techswipe.storage.mysql.test.TechContentProviderEntityBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TechContentJpaQueryRepositoryCustomImplTest extends AbstractDataJpaTest {

    @Autowired
    private TechContentJpaQueryRepository techContentJpaQueryRepository;

    @Test
    void 카테고리로_필터링해서_컨텐츠를_발행일_내림차순으로_조회한다() {
        // given
        TechContentProviderEntity providerEntity = entityAppender.append(TechContentProviderEntityBuilder.create());

        List<TechContentEntity> techContentEntities = entityAppender.appendAll(
                new TechContentEntityBuilder()
                        .provider(providerEntity)
                        .categories(TechCategory.SERVER, TechCategory.SW_ENGINEERING)
                        .publishedDate(LocalDate.of(2021, 1, 1))
                        .build()
                ,
                new TechContentEntityBuilder()
                        .provider(providerEntity)
                        .categories(TechCategory.WEB, TechCategory.SW_ENGINEERING)
                        .publishedDate(LocalDate.of(2021, 1, 2))
                        .build()
                ,
                new TechContentEntityBuilder()
                        .provider(providerEntity)
                        .categories(TechCategory.APP)
                        .publishedDate(LocalDate.of(2021, 1, 2))
                        .build()
        );

        // when
        List<TechContentEntity> result = techContentJpaQueryRepository.findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
                new PageParam(1, 10),
                List.of(TechCategory.SW_ENGINEERING)
        );

        // then
        assertThat(result).hasSize(2);
        assertThat(result.stream().map(TechContentEntity::getId).toList())
                .containsExactly(
                        techContentEntities.get(1).getId(),
                        techContentEntities.get(0).getId()
                );
    }

    @Test
    void 카테고리_조건을_2개이상_이용해서_컨테츠를_필터링해서_발행일_내림차순으로_조회한다() {
        // given
        TechContentProviderEntity providerEntity = entityAppender.append(TechContentProviderEntityBuilder.create());

        List<TechContentEntity> techContentEntities = entityAppender.appendAll(
                new TechContentEntityBuilder()
                        .provider(providerEntity)
                        .categories(TechCategory.SERVER, TechCategory.SW_ENGINEERING)
                        .publishedDate(LocalDate.of(2021, 1, 1))
                        .build()
                ,
                new TechContentEntityBuilder()
                        .provider(providerEntity)
                        .categories(TechCategory.WEB, TechCategory.SW_ENGINEERING)
                        .publishedDate(LocalDate.of(2021, 1, 2))
                        .build()
                ,
                new TechContentEntityBuilder()
                        .provider(providerEntity)
                        .categories(TechCategory.APP)
                        .publishedDate(LocalDate.of(2021, 1, 3))
                        .build()
        );

        // when
        List<TechContentEntity> result = techContentJpaQueryRepository.findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
                new PageParam(1, 10),
                List.of(TechCategory.SW_ENGINEERING, TechCategory.APP)
        );

        // then
        assertThat(result).hasSize(3);
        assertThat(result.stream().map(TechContentEntity::getId).toList())
                .containsExactly(
                        techContentEntities.get(2).getId(),
                        techContentEntities.get(1).getId(),
                        techContentEntities.get(0).getId()
                );
    }
}