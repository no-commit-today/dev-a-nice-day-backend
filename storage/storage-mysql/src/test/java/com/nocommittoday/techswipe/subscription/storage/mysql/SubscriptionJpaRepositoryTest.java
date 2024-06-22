package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.test.AbstractDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class SubscriptionJpaRepositoryTest extends AbstractDataJpaTest {

    @Autowired
    private SubscriptionJpaRepository subscriptionJpaRepository;

    @Test
    void 삭제되지_않은_엔티티_리스트를_조회할_수_있다() {
        // given
        final List<SubscriptionEntity> entities = List.of(
                new SubscriptionEntity(
                        1L,
                        TechContentProviderEntity.from(new TechContentProvider.Id(1)),
                        SubscriptionType.FEED,
                        SubscriptionInitType.NONE,
                        new SubscriptionData()
                ),
                new SubscriptionEntity(
                        2L,
                        TechContentProviderEntity.from(new TechContentProvider.Id(2)),
                        SubscriptionType.FEED,
                        SubscriptionInitType.NONE,
                        new SubscriptionData()
                ),
                new SubscriptionEntity(
                        3L,
                        TechContentProviderEntity.from(new TechContentProvider.Id(3)),
                        SubscriptionType.FEED,
                        SubscriptionInitType.NONE,
                        new SubscriptionData()
                )
        );
        entities.get(2).delete();
        subscriptionJpaRepository.saveAll(entities);

        // when
        final List<SubscriptionEntity> result = subscriptionJpaRepository.findAllByDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by("id"))
        );

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(SubscriptionEntity::getId)
                .containsExactlyInAnyOrder(1L, 2L);
    }

}