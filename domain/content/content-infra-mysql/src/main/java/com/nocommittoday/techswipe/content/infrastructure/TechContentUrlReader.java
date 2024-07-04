package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TechContentUrlReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public String get(TechContent.Id id) {
        return techContentJpaRepository.findUrlByIdAndDeletedIsFalse(id.value())
                .orElseThrow(() -> new TechContentNotFoundException(id));
    }
}
