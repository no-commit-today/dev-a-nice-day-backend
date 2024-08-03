package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
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
