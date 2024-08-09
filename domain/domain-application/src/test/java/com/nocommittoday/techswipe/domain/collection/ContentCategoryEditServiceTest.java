package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionCategoryNotApplicableException;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContent;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProvider;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.content.TechContentDeleter;
import com.nocommittoday.techswipe.domain.content.TechContentReader;
import com.nocommittoday.techswipe.domain.content.TechContentUpdater;
import com.nocommittoday.techswipe.domain.content.TechContentUrlExistsReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ContentCategoryEditServiceTest {

    @InjectMocks
    private ContentCategoryEditService contentCategoryEditService;

    @Mock
    private CollectedContentReader collectedContentReader;

    @Mock
    private CollectedContentUpdater collectedContentUpdater;

    @Mock
    private TechContentUrlExistsReader techContentUrlExistsReader;

    @Mock
    private TechContentReader techContentReader;

    @Mock
    private TechContentUpdater techContentUpdater;

    @Mock
    private TechContentDeleter techContentDeleter;

    @Captor
    private ArgumentCaptor<TechContent> techContentArgumentCaptor;

    @Test
    void 수정된_카테고리를_컨텐츠에_반영한다() {
        // given
        final CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1),
                new TechContentProviderId(2),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        ).categorize(List.of(CollectionCategory.SERVER));
        given(collectedContentReader.get(new CollectedContentId(1))).willReturn(collectedContent);
        given(techContentUrlExistsReader.existsIncludingDeleted(new TechContentId(1))).willReturn(true);

        final TechContent techContent = new TechContent(
                new TechContentId(1),
                new TechContentProvider(
                        new TechContentProviderId(2),
                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                        "name",
                        "url",
                        null
                ),
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "summary",
                List.of(TechCategory.APP)
        );

        given(techContentReader.getIncludingDeleted(new TechContentId(1))).willReturn(techContent);

        // when
        contentCategoryEditService.applyCategoryEdited(new CollectedContentId(1));

        // then
        then(techContentUpdater).should().update(techContentArgumentCaptor.capture());
        final TechContent updatedTechContent = techContentArgumentCaptor.getValue();
        assertThat(updatedTechContent.getCategories()).containsExactly(TechCategory.SERVER);
    }

    @Test
    void 카테고리가_없으면_예외를_발생시킨다() {
        // given
        final CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1),
                new TechContentProviderId(2),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        );
        given(collectedContentReader.get(new CollectedContentId(1))).willReturn(collectedContent);

        // when, then
        assertThatThrownBy(() -> contentCategoryEditService.applyCategoryEdited(new CollectedContentId(1)))
                .isInstanceOf(CollectionCategoryNotApplicableException.class);
    }

    @Test
    void url에_해당하는_컨텐츠가_없으면_종료된다() {
        // given
        final CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1),
                new TechContentProviderId(2),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        ).categorize(List.of(CollectionCategory.SERVER));
        given(collectedContentReader.get(new CollectedContentId(1))).willReturn(collectedContent);
        given(techContentUrlExistsReader.existsIncludingDeleted(new TechContentId(1))).willReturn(false);

        // when
        contentCategoryEditService.applyCategoryEdited(new CollectedContentId(1));

        // then
        then(techContentUpdater).shouldHaveNoInteractions();
        then(techContentDeleter).shouldHaveNoInteractions();
    }

    @Test
    void 컨텐츠가_필터링_상태이면_컨텐츠를_삭제한다() {
        // given
        final CollectedContent collectedContent = new CollectedContent(
                new CollectedContentId(1),
                new TechContentProviderId(2),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "content",
                "imageUrl"
        ).categorize(List.of(CollectionCategory.NON_DEV));
        given(collectedContentReader.get(new CollectedContentId(1))).willReturn(collectedContent);
        given(techContentUrlExistsReader.existsIncludingDeleted(new TechContentId(1))).willReturn(true);

        final TechContent techContent = new TechContent(
                new TechContentId(1),
                new TechContentProvider(
                        new TechContentProviderId(2),
                        TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                        "name",
                        "url",
                        null
                ),
                null,
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                "summary",
                List.of(TechCategory.APP)
        );

        given(techContentReader.getIncludingDeleted(new TechContentId(1))).willReturn(techContent);

        // when
        contentCategoryEditService.applyCategoryEdited(new CollectedContentId(1));

        // then
        then(techContentDeleter).should().delete(techContent.getId());
    }

}