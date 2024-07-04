package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentDeleter {

    private final TechContentJpaRepository techContentJpaRepository;

    public void delete(final TechContent.Id id) {
        techContentJpaRepository.deleteById(id.value());
    }
}
