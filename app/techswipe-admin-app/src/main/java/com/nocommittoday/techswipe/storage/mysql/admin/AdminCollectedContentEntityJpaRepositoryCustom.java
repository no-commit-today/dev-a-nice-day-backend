package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;

import java.util.List;

interface AdminCollectedContentEntityJpaRepositoryCustom {

    boolean updateSummaryById(Long id, String summary);

    boolean updateStatusAndCategoriesById(Long id, CollectionStatus status, List<CollectionCategory> categories);
}
