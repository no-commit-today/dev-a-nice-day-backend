package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TechContentUrlExistsReader {

    private final TechContentJpaRepository techContentJpaRepository;

    public boolean exists(final String url) {
        return techContentJpaRepository.existsByUrlAndDeletedIsFalse(url);
    }
}
