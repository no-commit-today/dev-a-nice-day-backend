package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.ContentCategoryEdit;
import com.nocommittoday.techswipe.collection.service.ContentCategoryEditService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContentCategoryEditController {

    private final ContentCategoryEditService categoryEditService;

    @PatchMapping("/api/collection/admin/collections/{collectionId}/categories")
    public void edit(
            @PathVariable Long collectionId,
            @RequestBody @Validated ContentCategoryEditRequest request
    ) {
        categoryEditService.editCategory(
                new CollectedContentId(collectionId),
                new ContentCategoryEdit(request.categories())
        );
    }

    @PostMapping("/api/collection/admin/collections/{collectionId}/apply-categories")
    public void apply(@PathVariable Long collectionId) {
        categoryEditService.applyCategoryEdited(new CollectedContentId(collectionId));
    }
}
