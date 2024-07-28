package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntityMapper;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TechContentProviderAppender {

    private final TechContentProviderJpaRepository repository;

    private final TechContentProviderEntityMapper mapper;

    public TechContentProviderId save(final TechContentProviderCreate command) {
        return new TechContentProviderId(
                Objects.requireNonNull(repository.save(mapper.from(command)).getId())
        );
    }
}
