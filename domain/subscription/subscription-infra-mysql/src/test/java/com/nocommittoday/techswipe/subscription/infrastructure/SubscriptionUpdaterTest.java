package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.Crawling;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SubscriptionUpdaterTest {

    @InjectMocks
    private SubscriptionUpdater subscriptionUpdater;

    @Mock
    private SubscriptionJpaRepository subscriptionJpaRepository;

    @Captor
    private ArgumentCaptor<SubscriptionEntity> captor;

    @Captor
    private ArgumentCaptor<TechContentProviderEntity> providerCaptor;

    @Test
    void 컨텐츠_제공자에_등록된_구독이_없으면_등록한다() {
        // given
        given(subscriptionJpaRepository.findByProvider(
                providerCaptor.capture()
        )).willReturn(Optional.empty());

        final SubscriptionEntity subscriptionEntity = mock(SubscriptionEntity.class);
        given(subscriptionEntity.getId()).willReturn(10L);
        given(subscriptionJpaRepository.save(captor.capture()))
                .willReturn(subscriptionEntity);

        // when
        subscriptionUpdater.update(new SubscriptionRegister(
                new TechContentProvider.TechContentProviderId(1),
                SubscriptionType.FEED,
                SubscriptionInitType.NONE,
                "feed-url",
                new ContentCrawling(
                        new Crawling(CrawlingType.INDEX, null, List.of(1)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1))
                ),
                List.of()
        ));

        // then
        assertThat(captor.getValue().getProvider().getId()).isEqualTo(1);

        final SubscriptionEntity entity = captor.getValue();
        assertThat(entity.getId()).isNull();
    }

    @Test
    void 컨텐츠_제공자에_등록된_구독이_있으면_삭제됐다면_복구_후_업데이트한다() {
        // given
        final SubscriptionEntity subscriptionEntity = mock(SubscriptionEntity.class);
        given(subscriptionEntity.getId()).willReturn(10L);
        given(subscriptionJpaRepository.findByProvider(
                providerCaptor.capture()
        )).willReturn(Optional.of(subscriptionEntity));

        given(subscriptionJpaRepository.save(captor.capture()))
                .willReturn(subscriptionEntity);

        // when
        final SubscriptionRegister subscriptionRegister = new SubscriptionRegister(
                new TechContentProvider.TechContentProviderId(1),
                SubscriptionType.FEED,
                SubscriptionInitType.NONE,
                "feed-url",
                new ContentCrawling(
                        new Crawling(CrawlingType.INDEX, null, List.of(1)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1)),
                        new Crawling(CrawlingType.INDEX, null, List.of(1))
                ),
                List.of()
        );
        subscriptionUpdater.update(subscriptionRegister);

        //
        assertThat(captor.getValue()).isEqualTo(subscriptionEntity);
        then(subscriptionEntity).should().restore();
        then(subscriptionEntity).should().update(subscriptionRegister);
    }
}