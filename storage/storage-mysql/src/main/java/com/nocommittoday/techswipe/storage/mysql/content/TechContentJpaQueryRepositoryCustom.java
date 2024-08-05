package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.PageParam;

import java.util.List;

interface TechContentJpaQueryRepositoryCustom {

    List<TechContentEntity> findAllWithAllByCategoryInOrderByPublishedDateDescAndNotDeleted(
            PageParam pageParam, List<TechCategory> categories
    );
}
