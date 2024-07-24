package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.service.TechContentProviderIconEditService;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TechContentProviderIconEditController {

    private final ImageStoreService imageStoreService;

    private final TechContentProviderIconEditService techContentProviderIconEditService;

    @PatchMapping("/api/content/admin/providers/{providerId}/icon")
    void edit(@PathVariable final Long providerId, @RequestBody @Validated TechContentProviderIconEditRequest request) {
        final ImageId iconId = Optional.ofNullable(request.iconUrl())
                .map(url -> imageStoreService.store(url, "provider"))
                .orElse(null);
        techContentProviderIconEditService.edit(new TechContentProvider.Id(providerId), iconId);
    }
}
