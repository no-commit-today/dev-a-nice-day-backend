package com.nocommittoday.techswipe.storage.mysql.bookmark;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkEntityJpaRepository
        extends JpaRepository<BookmarkEntity, Long>, BookmarkEntityJpaRepositoryCustom {

    Optional<BookmarkEntity> findByGroupAndContent(BookmarkGroupEntity group, TechContentEntity content);

    Optional<BookmarkEntity> findByGroupIdAndContentId(Long groupId, Long contentId);

    List<BookmarkEntity> findAllByGroup(BookmarkGroupEntity group);
}
