package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionIllegalProviderIdException;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentAppender;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentIdGenerator;
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
class ContentCollectServiceTest {

    @InjectMocks
    private ContentCollectService contentCollectService;

    @Mock
    private TechContentProviderExistsReader techContentProviderExistsReader;

    @Mock
    private CollectedContentIdGenerator idGenerator;

    @Mock
    private CollectedContentAppender collectedContentAppender;

    @Test
    void 존재하지_않는_providerId_일_경우_예외를_발생시킨다() {
        // given
        final ContentCollectCommand command = new ContentCollectCommand(
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
        assertThatThrownBy(() -> contentCollectService.collect(command))
                .isInstanceOf(CollectionIllegalProviderIdException.class);
    }

    @Test
    void 컨텐츠를_직접_수집할_수_있다() {
        // given
        final ContentCollectCommand command = new ContentCollectCommand(
                new TechContentProvider.Id(1),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(techContentProviderExistsReader.exists(new TechContentProvider.Id(1))).willReturn(true);
        given(idGenerator.nextId()).willReturn(new CollectedContent.Id(2));
        given(collectedContentAppender.save(command.toDomain(new CollectedContent.Id(2)))).willReturn(new CollectedContent.Id(2));

        // when
        final CollectedContent.Id collectedContentId = contentCollectService.collect(command);

        // then
        assertThat(collectedContentId).isEqualTo(new CollectedContent.Id(2));
    }
}