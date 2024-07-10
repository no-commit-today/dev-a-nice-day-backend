package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.test.storage.mysql.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectedContentJpaRepositoryCustomImplTest extends AbstractDataJpaTest {

    @Autowired
    private CollectedContentJpaRepository collectedContentJpaRepository;

    @Test
    void 컨텐츠_제공자에_해당하는_수집된_컨텐츠_url을_전체_조회할_수_있다() {
        // given
        final List<CollectedContentEntity> entities = List.of(
                new CollectedContentEntity(
                        idGenerator.nextId(),
                        CollectionStatus.INIT,
                        TechContentProviderEntity.from(new TechContentProvider.Id(10)),
                        "url1",
                        "title1",
                        LocalDate.of(2021, 1, 1),
                        "content1",
                        "image-url1",
                        null,
                        null
                ),
                new CollectedContentEntity(
                        idGenerator.nextId(),
                        CollectionStatus.INIT,
                        TechContentProviderEntity.from(new TechContentProvider.Id(10)),
                        "url2",
                        "title2",
                        LocalDate.of(2021, 1, 1),
                        "content2",
                        "image-url2",
                        null,
                        null
                ),
                new CollectedContentEntity(
                        idGenerator.nextId(),
                        CollectionStatus.INIT,
                        TechContentProviderEntity.from(new TechContentProvider.Id(11)),
                        "url3",
                        "title3",
                        LocalDate.of(2021, 1, 1),
                        "content3",
                        "image-url3",
                        null,
                        null
                )
        );
        entities.get(1).delete();
        collectedContentJpaRepository.saveAll(entities);

        // when
        final List<String> result = collectedContentJpaRepository.findAllUrlByProvider(
                TechContentProviderEntity.from(new TechContentProvider.Id(10))
        );

        // then
        assertThat(result).containsExactlyInAnyOrder("url1", "url2");
    }

    @Test
    void 여러_컨텐츠_제공자에_해당하는_수집된_컨텐츠_url을_전체_조회할_수_있다() {
        // given
        final List<CollectedContentEntity> entities = List.of(
                new CollectedContentEntity(
                        idGenerator.nextId(),
                        CollectionStatus.INIT,
                        TechContentProviderEntity.from(new TechContentProvider.Id(10)),
                        "url1",
                        "title1",
                        LocalDate.of(2021, 1, 1),
                        "content1",
                        "image-url1",
                        null,
                        null
                ),
                new CollectedContentEntity(
                        idGenerator.nextId(),
                        CollectionStatus.INIT,
                        TechContentProviderEntity.from(new TechContentProvider.Id(10)),
                        "url2",
                        "title2",
                        LocalDate.of(2021, 1, 1),
                        "content2",
                        "image-url2",
                        null,
                        null
                ),
                new CollectedContentEntity(
                        idGenerator.nextId(),
                        CollectionStatus.INIT,
                        TechContentProviderEntity.from(new TechContentProvider.Id(11)),
                        "url3",
                        "title3",
                        LocalDate.of(2021, 1, 1),
                        "content3",
                        "image-url3",
                        null,
                        null
                )
        );
        entities.get(1).delete();
        collectedContentJpaRepository.saveAll(entities);

        // when
        final List<String> result = collectedContentJpaRepository.findAllUrlByProviderIn(List.of(
                TechContentProviderEntity.from(new TechContentProvider.Id(10)),
                TechContentProviderEntity.from(new TechContentProvider.Id(11))
                )
        );

        // then
        assertThat(result).containsExactlyInAnyOrder("url1", "url2", "url3");
    }
}