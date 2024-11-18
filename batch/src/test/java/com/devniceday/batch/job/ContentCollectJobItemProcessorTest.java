package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectionStatus;
import com.devniceday.batch.domain.ContentSubscriber;
import com.devniceday.batch.domain.SubscribedContent;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.test.SubscriptionFixture;
import com.devniceday.module.idgenerator.IdGenerator;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ContentCollectJobItemProcessorTest {

    @InjectMocks
    private ContentCollectJobItemProcessor contentCollectJobItemProcessor;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private ContentSubscriber contentSubscriber;

    @Test
    void 구독하는_컨텐츠_데이터를_수집한다() throws Exception {
        // given
        BatchSubscriptionEntity entity = mock(BatchSubscriptionEntity.class);
        Subscription subscription = SubscriptionFixture.createFeed();
        given(subscriptionMapper.map(entity)).willReturn(subscription);
        given(contentSubscriber.subscribe(subscription))
                .willReturn(List.of(
                        new SubscribedContent(
                                subscription.id(),
                                "url",
                                null,
                                null,
                                null,
                                null
                        )
                ));
        given(idGenerator.nextId()).willReturn(123L);

        // when
        List<BatchCollectedContentEntity> result = contentCollectJobItemProcessor.process(entity);

        // then
        BatchCollectedContentEntity collectedContent = result.get(0);
        Assertions.assertThat(collectedContent.getId()).isEqualTo(123L);
        Assertions.assertThat(collectedContent.getProviderId()).isEqualTo(subscription.providerId());
        Assertions.assertThat(collectedContent.getSubscriptionId()).isEqualTo(subscription.id());
        Assertions.assertThat(collectedContent.getUrl()).isEqualTo("url");
        Assertions.assertThat(collectedContent.getTitle()).isNull();
        Assertions.assertThat(collectedContent.getPublishedDate()).isNull();
        Assertions.assertThat(collectedContent.getContent()).isNull();
        Assertions.assertThat(collectedContent.getImageUrl()).isNull();
        Assertions.assertThat(collectedContent.getStatus()).isEqualTo(CollectionStatus.COLLECTED);
    }
}