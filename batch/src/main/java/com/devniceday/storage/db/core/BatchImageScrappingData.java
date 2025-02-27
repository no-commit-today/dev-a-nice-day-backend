package com.devniceday.storage.db.core;


import com.devniceday.batch.domain.ImageScrappingType;

public class BatchImageScrappingData {

    private ImageScrappingType type;

    protected BatchImageScrappingData() {
    }

    public BatchImageScrappingData(ImageScrappingType type) {
        this.type = type;
    }

    public ImageScrappingType getType() {
        return type;
    }
}
