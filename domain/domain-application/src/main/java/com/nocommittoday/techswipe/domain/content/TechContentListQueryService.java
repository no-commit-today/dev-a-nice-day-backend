package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.provider.TechContentProviderQueryResult;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.domain.image.Image;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageReader;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TechContentListQueryService {

    private final TechContentCategorizedListReader techContentCategorizedListReader;
    private final ImageReader imageReader;

    public TechContentListQueryService(
            TechContentCategorizedListReader techContentCategorizedListReader, ImageReader imageReader
    ) {
        this.techContentCategorizedListReader = techContentCategorizedListReader;
        this.imageReader = imageReader;
    }

    public List<TechContentQueryResult> getList(PageParam pageParam, TechContentListQueryParam queryParam) {
        List<TechContentWithProvider> contents = techContentCategorizedListReader.getList(pageParam, queryParam.categories());
        Map<ImageId, String> imageIdToUrl = getImageIdStringMap(contents);
        return contents.stream()
                .map(content -> new TechContentQueryResult(
                                content.getId(),
                                new TechContentProviderQueryResult(
                                        content.getProvider().getId(),
                                        content.getProvider().getTitle(),
                                        content.getProvider().getUrl(),
                                        imageIdToUrl.getOrDefault(content.getProvider().getIconId(), null)
                                ),
                                content.getUrl(),
                                content.getTitle(),
                                content.getPublishedDate(),
                                imageIdToUrl.getOrDefault(content.getImageId(), null),
                                content.getSummary().getContent(),
                                content.getCategories()
                        )
                ).toList();
    }

    private Map<ImageId, String> getImageIdStringMap(List<TechContentWithProvider> contentList) {
        Set<ImageId> imageIds = new HashSet<>();
        contentList.stream()
                .map(TechContentWithProvider::getImageId)
                .filter(Objects::nonNull)
                .forEach(imageIds::add);
        contentList.stream()
                .map(TechContentWithProvider::getProvider)
                .map(TechContentProvider::getIconId)
                .filter(Objects::nonNull)
                .forEach(imageIds::add);
        return imageReader.getAll(imageIds).stream()
                .collect(Collectors.toMap(
                        Image::getId, Image::getUrl
                ));
    }

    public long count(TechContentListQueryParam queryParam) {
        return techContentCategorizedListReader.count(queryParam.categories());
    }
}
