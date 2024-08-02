package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionAlreadyCollectedException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionIllegalProviderIdException;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderExistsReader;
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
class ContentCollectServiceTest {

    @InjectMocks
    private ContentCollectService contentCollectService;

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
        final ContentCollectCommand command = new ContentCollectCommand(
                new TechContentProviderId(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProviderId(1))).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> contentCollectService.collect(command))
                .isInstanceOf(CollectionIllegalProviderIdException.class);
    }

    @Test
    void 이미_수집된_url_일_경우_예외를_발생시킨다() {
        // given
        final ContentCollectCommand command = new ContentCollectCommand(
                new TechContentProviderId(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProviderId(1))).willReturn(true);
        given(collectedContentUrlExistsReader.exists("url")).willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> contentCollectService.collect(command))
                .isInstanceOf(CollectionAlreadyCollectedException.class);
    }

    @Test
    void 컨텐츠를_직접_수집할_수_있다() {
        // given
        final ContentCollectCommand command = new ContentCollectCommand(
                new TechContentProviderId(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProviderId(1))).willReturn(true);
        given(idGenerator.nextId()).willReturn(new CollectedContentId(2));
        given(collectedContentAppender.save(command.toDomain(new CollectedContentId(2)))).willReturn(new CollectedContentId(2));

        // when
        final CollectedContentId collectedContentId = contentCollectService.collect(command);

        // then
        assertThat(collectedContentId).isEqualTo(new CollectedContentId(2));
    }
}