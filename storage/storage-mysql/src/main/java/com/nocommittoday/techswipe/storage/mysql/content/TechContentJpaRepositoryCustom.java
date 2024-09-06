package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.core.PageParam;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

interface TechContentJpaRepositoryCustom {

//    @Deprecated
    List<TechContentEntity> findAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
            PageParam pageParam, List<TechCategory> categories
    );

    List<TechContentEntity> findAllByGreaterThanContentCategoryInOrderByPublishedDateDescAndDeletedIsFalse(
            @Nullable TechContentId lastContentId, List<TechCategory> categories, int size
    );

    long countByCategoryInAndDeletedIsFalse(List<TechCategory> categories);

    List<TechContentEntity> findAllWithProviderOrderByPublishedDateDesc(PageParam pageParam);

    Optional<String> findUrlByIdAndDeletedIsFalse(Long id);
}
