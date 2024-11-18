package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.ImageScrapping;
import com.devniceday.batch.domain.ImageScrappingType;
import jakarta.annotation.Nullable;

public class ImageScrappingFixture {

    @Nullable
    private ImageScrappingType type;

    public static ImageScrapping create() {
        return new ImageScrappingFixture().build();
    }

    public ImageScrappingFixture type(ImageScrappingType type) {
        this.type = type;
        return this;
    }

    public ImageScrapping build() {
        fillRequiredFields();
        return new ImageScrapping(type);
    }

    private void fillRequiredFields() {
        if (type == null) {
            type = ImageScrappingType.OPEN_GRAPH_IMAGE;
        }
    }


}
