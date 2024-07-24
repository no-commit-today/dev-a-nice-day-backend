package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TechContentUrlExistsReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public boolean existsIncludingDeleted(final TechContentId id) {
        return techContentJpaRepository.existsById(id.value());
    }
}
