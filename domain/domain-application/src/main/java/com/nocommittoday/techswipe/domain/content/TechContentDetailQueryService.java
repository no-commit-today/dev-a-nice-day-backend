package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.image.ImageUrlReader;
import org.springframework.stereotype.Service;

@Service
public class TechContentDetailQueryService {

    private final TechContentReader techContentReader;
    private final ImageUrlReader imageUrlReader;

    public TechContentDetailQueryService(
            TechContentReader techContentReader,
            ImageUrlReader imageUrlReader
    ) {
        this.techContentReader = techContentReader;
        this.imageUrlReader = imageUrlReader;
    }

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