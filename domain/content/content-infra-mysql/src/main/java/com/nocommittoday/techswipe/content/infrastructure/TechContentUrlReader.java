package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TechContentUrlReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public TechContentUrlReader(TechContentJpaRepository techContentJpaRepository) {
        this.techContentJpaRepository = techContentJpaRepository;
    }

    public String get(TechContentId id) {
        return techContentJpaRepository.findUrlByIdAndDeletedIsFalse(id.value())
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
