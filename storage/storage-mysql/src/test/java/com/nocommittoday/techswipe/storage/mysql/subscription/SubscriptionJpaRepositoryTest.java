package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import com.nocommittoday.techswipe.storage.mysql.test.AbstractDataJpaTest;
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
        List<SubscriptionEntity> entities = List.of(
                new SubscriptionEntity(
                        1L,
                        TechContentProviderEntity.from(new TechContentProviderId(1)),
                        SubscriptionType.FEED,
                        SubscriptionType.NONE,
                        new SubscriptionData()
                ),
                new SubscriptionEntity(
                        2L,
                        TechContentProviderEntity.from(new TechContentProviderId(2)),
                        SubscriptionType.FEED,
                        SubscriptionType.NONE,
                        new SubscriptionData()
                ),
                new SubscriptionEntity(
                        3L,
                        TechContentProviderEntity.from(new TechContentProviderId(3)),
                        SubscriptionType.FEED,
                        SubscriptionType.NONE,
                        new SubscriptionData()
                )
        );
        entities.get(2).delete();
        subscriptionJpaRepository.saveAll(entities);

        // when
        List<SubscriptionEntity> result = subscriptionJpaRepository.findAllByDeletedIsFalse(
                PageRequest.of(0, 10, Sort.by("id"))
        );

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(SubscriptionEntity::getId)
                .containsExactlyInAnyOrder(1L, 2L);
    }

}