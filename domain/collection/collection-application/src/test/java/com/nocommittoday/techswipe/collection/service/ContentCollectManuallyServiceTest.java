package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionAlreadyCollectedException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionIllegalProviderIdException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUrlExistsReader;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderExistsReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContentCollectManuallyServiceTest {

    @InjectMocks
    private ContentCollectManuallyService contentCollectManuallyService;

    @Mock
    private TechContentProviderExistsReader techContentProviderExistsReader;

    @Mock
    private CollectedContentIdGenerator idGenerator;

    @Mock
    private CollectedContentAppender collectedContentAppender;

    @Mock
    private CollectedContentUrlExistsReader collectedContentUrlExistsReader;

    @Test
    void 존재하지_않는_providerId_일_경우_예외를_발생시킨다() {
        // given
        final ContentCollectManuallyCommand command = new ContentCollectManuallyCommand(
                new TechContentProvider.Id(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProvider.Id(1))).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> contentCollectManuallyService.collect(command))
                .isInstanceOf(CollectionIllegalProviderIdException.class);
    }

    @Test
    void 이미_수집된_url_일_경우_예외를_발생시킨다() {
        // given
        final ContentCollectManuallyCommand command = new ContentCollectManuallyCommand(
                new TechContentProvider.Id(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProvider.Id(1))).willReturn(true);
        given(collectedContentUrlExistsReader.exists("url")).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> contentCollectManuallyService.collect(command))
                .isInstanceOf(CollectionAlreadyCollectedException.class);
    }

    @Test
    void 컨텐츠를_직접_수집할_수_있다() {
        // given
        final ContentCollectManuallyCommand command = new ContentCollectManuallyCommand(
                new TechContentProvider.Id(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProvider.Id(1))).willReturn(true);
        given(idGenerator.nextId()).willReturn(new CollectedContentId(2));
        given(collectedContentAppender.save(command.toDomain(new CollectedContentId(2)))).willReturn(new CollectedContentId(2));

        // when
        final CollectedContentId collectedContentId = contentCollectManuallyService.collect(command);

        // then
        assertThat(collectedContentId).isEqualTo(new CollectedContentId(2));
    }
}