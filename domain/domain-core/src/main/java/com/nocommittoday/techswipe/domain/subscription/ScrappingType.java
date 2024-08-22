package com.nocommittoday.techswipe.domain.subscription;

public enum ScrappingType {

    NONE(true, true, true, true),
    INDEX(true, true, true, true),
    SELECTOR(true, true, true, true),
    TITLE(true, false, false, false),
    OPEN_GRAPH_IMAGE(false, false, false, true),
    ;

    private final boolean titleSupported;
    private final boolean dateSupported;
    private final boolean contentSupported;
    private final boolean imageSupported;

    ScrappingType(boolean titleSupported, boolean dateSupported, boolean contentSupported, boolean imageSupported) {
        this.titleSupported = titleSupported;
        this.dateSupported = dateSupported;
        this.contentSupported = contentSupported;
        this.imageSupported = imageSupported;
    }

    public boolean isTitleSupported() {
        return titleSupported;
    }

    public boolean isDateSupported() {
        return dateSupported;
    }

    public boolean isContentSupported() {
        return contentSupported;
    }

    public boolean isImageSupported() {
        return imageSupported;
    }
}
