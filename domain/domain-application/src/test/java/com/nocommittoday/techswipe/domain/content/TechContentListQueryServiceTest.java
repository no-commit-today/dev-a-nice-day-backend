package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkCheckerCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentListQueryServiceTest {

    @InjectMocks
    private TechContentListQueryService techContentListQueryService;

    @Mock
    private TechContentListQueryReader techContentListQueryReader;

    @Mock
    private TechContentCountReaderLocalCache techContentCountReader;

    @Mock
    private BookmarkCheckerCreator bookmarkCheckerCreator;

    @Test
    void 카운트를_조회할_때는_카테고리_리스트를_중복_제거_후_정렬해서_넘겨준다() {
        // given
        TechContentListQueryParam param = new TechContentListQueryParam(List.of(
                TechCategory.SERVER, TechCategory.SERVER, TechCategory.AI, TechCategory.AI, TechCategory.DATA_ENGINEERING
        ));
        given(techContentCountReader.get(List.of(
                TechCategory.SERVER, TechCategory.DATA_ENGINEERING, TechCategory.AI
        ))).willReturn(123L);

        // when
        long count = techContentListQueryService.count(param);

        // then
        assertThat(count).isEqualTo(123L);
    }
}