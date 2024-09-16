package com.nocommittoday.techswipe.storage.mysql.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkEntityJpaRepository extends JpaRepository<BookmarkEntity, Long> {
}
