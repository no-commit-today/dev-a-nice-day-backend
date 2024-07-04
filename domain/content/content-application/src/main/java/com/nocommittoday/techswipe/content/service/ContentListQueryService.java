package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.infrastructure.ContentCategorizedListReader;
import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.infrastructure.ImageReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentListQueryService {

    private final ContentCategorizedListReader contentCategorizedListReader;
    private final ImageReader imageReader;

    public List<ContentQueryResult> getList(final PageParam pageParam, final ContentListQueryParam queryParam) {
        final List<TechContent> contents = contentCategorizedListReader.getList(pageParam, queryParam.categories());
        final Map<Image.Id, String> imageIdToUrl = getImageIdStringMap(contents);
        return contents.stream()
                .map(content -> new ContentQueryResult(
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
                                content.getSummary(),
                                content.getCategories()
                        )
                ).toList();
    }

    private Map<Image.Id, String> getImageIdStringMap(final List<TechContent> contentList) {
        final Set<Image.Id> imageIds = new HashSet<>();
        contentList.stream()
                .map(TechContent::getImageId)
                .filter(Objects::nonNull)
                .forEach(imageIds::add);
        contentList.stream()
                .map(TechContent::getProvider)
                .map(TechContentProvider::getIconId)
                .filter(Objects::nonNull)
                .forEach(imageIds::add);
        return imageReader.getAll(imageIds).stream()
                .collect(Collectors.toMap(
                        Image::getId, Image::getUrl
                ));
    }

    public long count(final ContentListQueryParam queryParam) {
        return contentCategorizedListReader.count(queryParam.categories());
    }
}
