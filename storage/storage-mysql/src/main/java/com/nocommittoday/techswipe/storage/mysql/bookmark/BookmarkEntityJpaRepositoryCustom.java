package com.nocommittoday.techswipe.storage.mysql.bookmark;

import java.util.List;

interface BookmarkEntityJpaRepositoryCustom {

    long countByGroupIdAndContentNotDeleted(Long groupId);

    long countByUserIdAndContentNotDeleted(Long userId);

    List<BookmarkEntity> findAllWithGroupAndContentByGroupIdAndContentNotDeleted(Long groupId);

    List<BookmarkEntity> findAllByUserIdAndContentNotDeleted(Long userId);
}
