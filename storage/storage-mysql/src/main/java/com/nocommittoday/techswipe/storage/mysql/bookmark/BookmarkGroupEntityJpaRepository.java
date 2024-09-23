package com.nocommittoday.techswipe.storage.mysql.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkGroupEntityJpaRepository extends JpaRepository<BookmarkGroupEntity, Long> {

    Optional<BookmarkGroupEntity> findByUserIdAndName(Long userId, String name);

    List<BookmarkGroupEntity> findAllByUserId(Long userId);
}
