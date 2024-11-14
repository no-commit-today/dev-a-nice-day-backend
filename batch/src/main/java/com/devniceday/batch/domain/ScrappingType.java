package com.devniceday.batch.domain;

public enum ScrappingType {

    NONE(true, true, true, true, false),
    INDEX(true, true, true, true, true),
    SELECTOR(true, true, true, true, true),
    TITLE(true, false, false, false, false),
    OPEN_GRAPH_IMAGE(false, false, false, true, false),
    ;

    private final boolean titleSupported;
    private final boolean dateSupported;
    private final boolean contentSupported;
    private final boolean imageSupported;
    private final boolean listSupported;

    ScrappingType(
            boolean titleSupported,
            boolean dateSupported,
            boolean contentSupported,
            boolean imageSupported,
            boolean listSupported
    ) {
        this.titleSupported = titleSupported;
        this.dateSupported = dateSupported;
        this.contentSupported = contentSupported;
        this.imageSupported = imageSupported;
        this.listSupported = listSupported;
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

    public boolean isListSupported() {
        return listSupported;
    }
}
