package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionIllegalCategoryException;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionCategoryListTest {

    @Test
    void 생성할_때_카테고리_중복은_제거한다() {
        // given
        // when
        CollectionCategoryList collectionCategoryList = CollectionCategoryList.create(List.of(
                CollectionCategory.SERVER,
                CollectionCategory.SERVER,
                CollectionCategory.DATA_ENGINEERING
        ));

        // then
        assertThat(collectionCategoryList.getContent()).containsOnly(
                CollectionCategory.SERVER,
                CollectionCategory.DATA_ENGINEERING
        );
    }

    @Test
    void 생성할_때_카테고리는_이름순으로_정렬된다() {
        // given
        // when
        CollectionCategoryList collectionCategoryList = CollectionCategoryList.create(List.of(
                CollectionCategory.SERVER,
                CollectionCategory.AI,
                CollectionCategory.DATA_ENGINEERING
        ));

        // then
        assertThat(collectionCategoryList.getContent()).containsExactly(
                CollectionCategory.AI,
                CollectionCategory.DATA_ENGINEERING,
                CollectionCategory.SERVER
        );
    }

    @Test
    void 카테고리_개수는_1개이상_3개이하_이다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> CollectionCategoryList.create(List.of()))
                .isInstanceOf(CollectionIllegalCategoryException.class);
        CollectionCategoryList.create(List.of(CollectionCategory.SERVER));
        CollectionCategoryList.create(List.of(CollectionCategory.SERVER, CollectionCategory.AI));
        CollectionCategoryList.create(List.of(CollectionCategory.SERVER, CollectionCategory.AI, CollectionCategory.DATA_ENGINEERING));
        assertThatThrownBy(() -> CollectionCategoryList.create(List.of(CollectionCategory.SERVER, CollectionCategory.AI, CollectionCategory.DATA_ENGINEERING, CollectionCategory.DEVOPS)))
                .isInstanceOf(CollectionIllegalCategoryException.class);
    }

    @Test
    void 기술_카테고리에_사용되지_않는_카테고리가_포함되면_true를_반환한다() {
        // given
        // when
        CollectionCategoryList collectionCategoryList = CollectionCategoryList.create(List.of(
                CollectionCategory.SERVER,
                CollectionCategory.NON_DEV
        ));

        // then
        assertThat(collectionCategoryList.containsUnused()).isTrue();
    }

    @Test
    void 기술_카테고리에_사용되는_카테고리만_포함되어있으면_false를_반환한다() {
        // given
        // when
        CollectionCategoryList collectionCategoryList = CollectionCategoryList.create(List.of(
                CollectionCategory.SERVER,
                CollectionCategory.AI
        ));

        // then
        assertThat(collectionCategoryList.containsUnused()).isFalse();
    }

    @Test
    void 기술_카테고리로_변환한다() {
        // given
        // when
        CollectionCategoryList collectionCategoryList = CollectionCategoryList.create(List.of(
                CollectionCategory.AI,
                CollectionCategory.SERVER
        ));

        // then
        assertThat(collectionCategoryList.toTechCategories()).containsExactly(
                TechCategory.AI,
                TechCategory.SERVER
        );
    }
}