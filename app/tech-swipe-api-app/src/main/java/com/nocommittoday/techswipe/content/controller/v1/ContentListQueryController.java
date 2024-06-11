package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.controller.v1.request.ContentListQueryRequest;
import com.nocommittoday.techswipe.content.controller.v1.response.ContentResponse;
import com.nocommittoday.techswipe.content.application.port.in.ContentListQuery;
import com.nocommittoday.techswipe.content.application.port.in.ContentListQueryParam;
import com.nocommittoday.techswipe.content.application.port.in.ContentResult;
import com.nocommittoday.techswipe.content.application.port.in.ProviderResult;
import com.nocommittoday.techswipe.image.service.ImageUrlResult;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.core.adapter.in.web.servlet.ListResponse;
import com.nocommittoday.techswipe.core.adapter.in.web.servlet.PageRequest;
import com.nocommittoday.techswipe.image.service.ImageUrlQueryService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContentListQueryController {

    private final ContentListQuery contentListQuery;
    private final ImageUrlQueryService imageUrlQueryService;

    @GetMapping("/api/content/v1/contents")
    ResponseEntity<ListResponse<ContentResponse>> getList(
            final @ModelAttribute @Valid PageRequest pageRequest,
            final @ModelAttribute ContentListQueryRequest request
    ) {

        final List<ContentResult> contentList = contentListQuery.getList(
                pageRequest.toPageParam(), new ContentListQueryParam(request.categories())
        );
        final Map<Image.ImageId, String> imageIdToUrl = getImageIdStringMap(contentList);

        return ResponseEntity.ok(new ListResponse<>(contentList.stream()
                .map(content -> new ContentResponse(
                        content.id().value(),
                        content.url(),
                        content.title(),
                        content.summary(),
                        content.imageId() == null ? null : imageIdToUrl.get(content.imageId()),
                        content.categories(),
                        content.provider().id().value(),
                        content.provider().title(),
                        content.provider().url(),
                        content.provider().iconId() == null ? null : imageIdToUrl.get(content.provider().iconId())
                )).toList()));
    }

    @NonNull
    private Map<Image.ImageId, String> getImageIdStringMap(final List<ContentResult> contentList) {
        final List<Image.ImageId> imageIds = new ArrayList<>();
        contentList.stream()
                .map(ContentResult::imageId)
                .filter(Objects::nonNull)
                .forEach(imageIds::add);
        contentList.stream()
                .map(ContentResult::provider)
                .map(ProviderResult::iconId)
                .filter(Objects::nonNull)
                .forEach(imageIds::add);
        return imageUrlQueryService.getAll(imageIds).stream()
                .collect(Collectors.toMap(ImageUrlResult::id, ImageUrlResult::url));
    }
}
