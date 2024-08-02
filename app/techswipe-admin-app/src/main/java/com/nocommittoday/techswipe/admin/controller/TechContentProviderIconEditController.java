package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.service.TechContentProviderIconEditService;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TechContentProviderIconEditController {

    private final ImageStoreService imageStoreService;
    private final TechContentProviderIconEditService techContentProviderIconEditService;

    public TechContentProviderIconEditController(
            ImageStoreService imageStoreService,
            TechContentProviderIconEditService techContentProviderIconEditService
    ) {
        this.imageStoreService = imageStoreService;
        this.techContentProviderIconEditService = techContentProviderIconEditService;
    }

    @PatchMapping("/api/content/admin/providers/{providerId}/icon")
    void edit(@PathVariable Long providerId, @RequestBody @Validated TechContentProviderIconEditRequest request) {
        ImageId iconId = Optional.ofNullable(request.iconUrl())
                .map(url -> imageStoreService.store(url, "provider").get())
                .orElse(null);
        techContentProviderIconEditService.edit(new TechContentProviderId(providerId), iconId);
    }
}
