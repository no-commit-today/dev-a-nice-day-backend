package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.test.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentJpaRepositoryCustomImplTest extends AbstractDataJpaTest {

    @Autowired
    private TechContentProviderJpaRepository techContentProviderJpaRepository;

    @Autowired
    private TechContentJpaRepository techContentJpaRepository;

    @Test
    void 모든_컨텐츠를_발행일_내림차순으로_조회한다() {
        // given
        final TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                11L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        final List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        1L,
                        providerEntity,
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        2L,
                        providerEntity,
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        3L,
                        providerEntity,
                        null,
                        "url-3",
                        "title-3",
                        "summary-3",
                        LocalDate.of(2021, 1, 3)
                )
        );
        techContentEntities.get(0).addCategory(TechCategory.SERVER);
        techContentEntities.get(0).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(1).addCategory(TechCategory.WEB);
        techContentEntities.get(1).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(2).addCategory(TechCategory.APP);
        techContentJpaRepository.saveAll(techContentEntities);

        // when
        final List<TechContentEntity> result = techContentJpaRepository.findAllWithProviderOrderByPublishedDateDesc(
                PageRequest.of(0, 10));

        // then
        assertThat(result).hasSize(3);
        assertThat(result.stream().map(TechContentEntity::getId).toList())
                .containsExactly(3L, 2L, 1L);
    }

    @Test
    void 카테고리로_필터링해서_컨텐츠를_발행일_내림차순으로_조회한다() {
        // given
        final TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                11L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        final List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        1L,
                        providerEntity,
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        2L,
                        providerEntity,
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        3L,
                        providerEntity,
                        null,
                        "url-3",
                        "title-3",
                        "summary-3",
                        LocalDate.of(2021, 1, 3)
                )
        );
        techContentEntities.get(0).addCategory(TechCategory.SERVER);
        techContentEntities.get(0).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(1).addCategory(TechCategory.WEB);
        techContentEntities.get(1).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(2).addCategory(TechCategory.APP);
        techContentJpaRepository.saveAll(techContentEntities);

        // when
        final List<TechContentEntity> result = techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
                PageRequest.of(0, 10),
                List.of(TechCategory.SW_ENGINEERING)
        );

        // then
        assertThat(result).hasSize(2);
        assertThat(result.stream().map(TechContentEntity::getId).toList())
                .containsExactly(2L, 1L);
    }

    @Test
    void 카테고리_조건을_2개이상_이용해서_컨테츠를_필터링해서_발행일_내림차순으로_조회한다() {
        // given
        final TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                11L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        final List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        1L,
                        providerEntity,
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        2L,
                        providerEntity,
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        3L,
                        providerEntity,
                        null,
                        "url-3",
                        "title-3",
                        "summary-3",
                        LocalDate.of(2021, 1, 3)
                )
        );
        techContentEntities.get(0).addCategory(TechCategory.SERVER);
        techContentEntities.get(0).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(1).addCategory(TechCategory.WEB);
        techContentEntities.get(1).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(2).addCategory(TechCategory.APP);
        techContentJpaRepository.saveAll(techContentEntities);

        // when
        final List<TechContentEntity> result = techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
                PageRequest.of(0, 10),
                List.of(TechCategory.SW_ENGINEERING, TechCategory.APP)
        );

        // then
        assertThat(result).hasSize(3);
        assertThat(result.stream().map(TechContentEntity::getId).toList())
                .containsExactly(3L, 2L, 1L);
    }

}