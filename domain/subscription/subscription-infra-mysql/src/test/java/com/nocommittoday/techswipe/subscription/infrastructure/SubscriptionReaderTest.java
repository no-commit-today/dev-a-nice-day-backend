package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionNotFoundException;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SubscriptionReaderTest {

    @InjectMocks
    private SubscriptionReader subscriptionReader;

    @Mock
    private SubscriptionJpaRepository subscriptionRepository;

    @Captor
    private ArgumentCaptor<TechContentProviderEntity> providerCaptor;

    @Test
    void 컨텐츠_제공자에_등록된_구독이_없으면_예외를_던진다() {
        // given
        given(subscriptionRepository.findByProvider(
                providerCaptor.capture()
        )).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> subscriptionReader.getByProviderId(
                new TechContentProviderId(1)))
                .isInstanceOf(SubscriptionNotFoundException.class);

        assertThat(providerCaptor.getValue().getId()).isEqualTo(1L);
    }

    @Test
    void 컨텐츠_제공자에_등록된_구독이_삭제되었으면_예외를_던진다() {
        // given
        final SubscriptionEntity entity = mock(SubscriptionEntity.class);
        given(subscriptionRepository.findByProvider(
                providerCaptor.capture()
        )).willReturn(Optional.of(entity));
        given(entity.isUsed()).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> subscriptionReader.getByProviderId(
                new TechContentProviderId(1)))
                .isInstanceOf(SubscriptionNotFoundException.class);

        assertThat(providerCaptor.getValue().getId()).isEqualTo(1L);
    }

}