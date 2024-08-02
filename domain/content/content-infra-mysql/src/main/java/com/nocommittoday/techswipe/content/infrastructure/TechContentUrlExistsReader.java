package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentUrlExistsReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentUrlExistsReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    public boolean existsIncludingDeleted(TechContentId id) {
        return techContentJpaRepository.existsById(id.value());
    }
}
