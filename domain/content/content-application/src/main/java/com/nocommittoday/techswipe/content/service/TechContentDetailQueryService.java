package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.infrastructure.TechContentReader;
import com.nocommittoday.techswipe.image.infrastructure.ImageUrlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechContentDetailQueryService {

    private final TechContentReader techContentReader;

    private final ImageUrlReader imageUrlReader;

    public TechContentDetailQueryResult get(TechContentId id) {
        TechContent techContent = techContentReader.get(id);
        return new TechContentDetailQueryResult(
                techContent.getId(),
                techContent.getUrl(),
                techContent.getTitle(),
                techContent.getPublishedDate(),
                imageUrlReader.getOrNull(techContent.getImageId()),
                techContent.getSummary(),
                techContent.getCategories(),
                techContent.getProvider().getId(),
                techContent.getProvider().getType(),
                techContent.getProvider().getTitle(),
                techContent.getProvider().getUrl(),
                imageUrlReader.getOrNull(techContent.getProvider().getIconId())
        );
    }
}
