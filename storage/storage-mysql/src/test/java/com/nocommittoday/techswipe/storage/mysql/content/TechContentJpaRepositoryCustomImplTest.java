package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.core.domain.PageParam;
import com.nocommittoday.techswipe.storage.mysql.test.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                idGenerator.nextId(),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        idGenerator.nextId(),
                        providerEntity,
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        idGenerator.nextId(),
                        providerEntity,
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        idGenerator.nextId(),
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
        List<TechContentEntity> result = techContentJpaRepository.findAllWithProviderOrderByPublishedDateDesc(
                new PageParam(1, 10));

        // then
        assertThat(result).hasSize(3);
        assertThat(result.stream().map(TechContentEntity::getId).toList())
                .containsExactly(
                        techContentEntities.get(2).getId(),
                        techContentEntities.get(1).getId(),
                        techContentEntities.get(0).getId()
                );
    }

    @Test
    void 카테고리로_필터링해서_컨텐츠를_발행일_내림차순으로_조회한다() {
        // given
        TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                idGenerator.nextId(),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        idGenerator.nextId(),
                        providerEntity,
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        idGenerator.nextId(),
                        providerEntity,
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        idGenerator.nextId(),
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
        List<TechContentEntity> result = techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
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
        TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                idGenerator.nextId(),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        idGenerator.nextId(),
                        providerEntity,
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        idGenerator.nextId(),
                        providerEntity,
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        idGenerator.nextId(),
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
        List<TechContentEntity> result = techContentJpaRepository.findAllWithProviderByCategoryInOrderByPublishedDateDesc(
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

    @Test
    void 카테고리에_해당하는_컨텐츠_개수를_조회한다() {
        // given
        List<TechContentEntity> techContentEntities = List.of(
                new TechContentEntity(
                        idGenerator.nextId(),
                        techContentProviderJpaRepository.getReferenceById(10L),
                        null,
                        "url-1",
                        "title-1",
                        "summary-1",
                        LocalDate.of(2021, 1, 1)
                ),
                new TechContentEntity(
                        idGenerator.nextId(),
                        techContentProviderJpaRepository.getReferenceById(10L),
                        null,
                        "url-2",
                        "title-2",
                        "summary-2",
                        LocalDate.of(2021, 1, 2)
                ), new TechContentEntity(
                        idGenerator.nextId(),
                        techContentProviderJpaRepository.getReferenceById(11L),
                        null,
                        "url-3",
                        "title-3",
                        "summary-3",
                        LocalDate.of(2021, 1, 3)
                ),
                new TechContentEntity(
                        idGenerator.nextId(),
                        techContentProviderJpaRepository.getReferenceById(11L),
                        null,
                        "url-4",
                        "title-4",
                        "summary-4",
                        LocalDate.of(2021, 1, 3)
                )
        );
        // 둘 중 한개 카테고리 선택됨 -> + 1
        techContentEntities.get(0).addCategory(TechCategory.SERVER);
        techContentEntities.get(0).addCategory(TechCategory.SW_ENGINEERING);
        // 카테고리 선택되었으나 삭제됨 -> + 0
        techContentEntities.get(1).addCategory(TechCategory.WEB);
        techContentEntities.get(1).addCategory(TechCategory.SW_ENGINEERING);
        techContentEntities.get(1).delete();
        // 한개 중 한개 카테고리 선택 -> + 1
        techContentEntities.get(2).addCategory(TechCategory.APP);
        // 둘 중 두개 카테고리 선택 -> + 1
        techContentEntities.get(3).addCategory(TechCategory.APP);
        techContentEntities.get(3).addCategory(TechCategory.SW_ENGINEERING);
        techContentJpaRepository.saveAll(techContentEntities);

        // when
        long result = techContentJpaRepository.countByCategoryInAndDeletedIsFalse(
                List.of(TechCategory.SW_ENGINEERING, TechCategory.APP)
        );

        // then
        assertThat(result).isEqualTo(3);
    }

    @Test
    void 컨텐츠_ID로_URL을_조회한다() {
        // given
        TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                idGenerator.nextId(),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        TechContentEntity techContentEntity = techContentJpaRepository.save(new TechContentEntity(
                idGenerator.nextId(),
                providerEntity,
                null,
                "url-1",
                "title-1",
                "summary-1",
                LocalDate.of(2021, 1, 1)
        ));

        // when
        String result = techContentJpaRepository.findUrlByIdAndDeletedIsFalse(techContentEntity.getId()).orElse(null);

        // then
        assertThat(result).isEqualTo("url-1");
    }

    @Test
    void 컨텐츠_ID로_URL을_조회한다_삭제된_컨텐츠는_조회되지_않는다() {
        // given
        TechContentProviderEntity providerEntity = techContentProviderJpaRepository.save(new TechContentProviderEntity(
                idGenerator.nextId(),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "provider-title",
                "provider-url",
                null
        ));

        TechContentEntity techContentEntity = techContentJpaRepository.save(new TechContentEntity(
                idGenerator.nextId(),
                providerEntity,
                null,
                "url-1",
                "title-1",
                "summary-1",
                LocalDate.of(2021, 1, 1)
        ));
        techContentEntity.delete();

        // when
        String result = techContentJpaRepository.findUrlByIdAndDeletedIsFalse(techContentEntity.getId()).orElse(null);

        // then
        assertThat(result).isNull();
    }

}